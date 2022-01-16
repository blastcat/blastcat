package com.blast.springDemo.easyExcel.config;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.blast.springDemo.easyExcel.annotation.PoiSupport;
import com.blast.springDemo.core.exception.ServiceException;
import com.blast.springDemo.core.vo.ApiResult;
import com.blast.springDemo.core.vo.ResultUtil;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Log4j2
@ControllerAdvice
public class ExcelExportAdvice implements ResponseBodyAdvice<ApiResult> {

    private final String poiHeaderKey = "__poi__";

    public enum ExportType {
        Excel, Word, Image, PDF
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        PoiSupport poiSupport = methodParameter.getMethodAnnotation(PoiSupport.class);
        return Objects.nonNull(poiSupport);
    }

    @SneakyThrows
    @Override
    public ApiResult<?> beforeBodyWrite(ApiResult apiResult, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        PoiSupport poiSupport = methodParameter.getMethodAnnotation(PoiSupport.class);
        assert poiSupport != null;
        List<String> headers = serverHttpRequest.getHeaders().getOrDefault(poiHeaderKey, new ArrayList<>());
        //如果请求本身是异常的，直接返回
        assert apiResult != null;
        if (!apiResult.isSuccess()) return apiResult;
        //如果没有强制指定使用POI
        if (!poiSupport.force() && headers.isEmpty()) return apiResult;
        ResultUtil resultUtil = new ResultUtil(apiResult);
        if (poiSupport.type() == ExportType.Excel) {
            if (StringUtils.isEmpty(poiSupport.key()) && resultUtil.isArray()) { //如果是数组
                this.repeatedWrite(poiSupport, resultUtil.getList(), serverHttpResponse);
            } else {
                throw new ServiceException("获取的数据类型与您期望的导出文件格式不匹配！");
            }
        }
        return null;
    }

    /**
     * 重复多次写入
     */
    public void repeatedWrite(PoiSupport poiSupport, Collection<?> collection, ServerHttpResponse response) throws IOException {
        // 方法1 如果写到同一个sheet
        String fileName = StrUtil.isBlank(poiSupport.value()) ? UUID.randomUUID().toString() : poiSupport.value();
        ExcelWriter excelWriter = null;
        try {
            fillDownloadHeader(response.getHeaders(), fileName, poiSupport.suffix());
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(response.getBody(),poiSupport.entity()).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet(poiSupport.value()).build();
            log.info("Excel导出文件:{}{}，共{}条数据。", fileName, poiSupport.suffix(), collection.size());
            List<?> temp;
            for (int i = 0; i < 5; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                temp = CollectionUtil.toList(collection.toArray());
                excelWriter.write(temp, writeSheet);
                temp.clear();
            }
            log.info("Excel导出文件:{}{}，完毕。", fileName, poiSupport.suffix());
        } catch (Exception e) {
            log.warn(e);
            throw new ServiceException("文件导出失败，下载过程异常！");
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @SneakyThrows
    public void fillDownloadHeader(HttpHeaders headers, String fileName, String suffix) {
        headers.set(HttpHeaders.ACCEPT_CHARSET, "UTF-8");
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName + suffix, "UTF-8"));
    }

}
