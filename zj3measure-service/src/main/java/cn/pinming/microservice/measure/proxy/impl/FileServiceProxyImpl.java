package cn.pinming.microservice.measure.proxy.impl;

import cn.pinming.core.common.util.StringUtil;
import cn.pinming.microservice.measure.proxy.FileServiceProxy;
import cn.pinming.model.FileInfos;
import cn.pinming.model.dto.FileQueryDto;
import cn.pinming.v2.common.api.dto.FileDto;
import cn.pinming.v2.common.api.service.FileService;
import cn.pinming.zhuang.api.file.service.FileDtoService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jin on 2020-03-17.
 */
@Slf4j
@Component
public class FileServiceProxyImpl implements FileServiceProxy {

    @Reference(parameters = {
            "fileDownloadUrl.timeout","2000"
    })
    private FileInfos fileInfos;

    @Reference
    private FileService fileService;

    @Reference(
            parameters = {
                    "getFileByFileUrl.timeout", "2000"
            }
    )
    private FileDtoService fileDtoService;



    @Override
    public String getFileDownLoadUrl(@NonNull String fileUuid) {
        String trueUuid = fileUuid;
        int pointIndex = trueUuid.indexOf(".");
        if (pointIndex > 0 && trueUuid.contains("-")) {
            trueUuid = trueUuid.substring(0, pointIndex);
        }
        if (trueUuid.contains(".") && !trueUuid.contains("-")) {
            try {
                cn.pinming.zhuang.api.upload.dto.FileDto fileDto = fileDtoService.getFileByFileUrl(trueUuid);
                if (fileDto != null) {
                    String fileUUID = fileDto.getFileUuid();
                    if (!StringUtil.isEmpty(fileUUID)) {
                        trueUuid = fileUUID;
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        FileQueryDto down = new FileQueryDto();
        down.setFileId(trueUuid);
        down.setUrlType((byte) 1);
        down.setPicType((byte) 4);
        down.setBrowserType((byte)2);
        String url;
        try {
            url = fileInfos.fileDownloadUrl(down);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("转换下载地址失败,mock null", e);
            }
            url = null;
        }
        if (url == null && log.isWarnEnabled()) {
            log.warn("fileUuid param {},{}查询下载地址为空，请检查", fileUuid, down);
        }
        return url;
    }

    @Override
    public void confirmFiles(List<String> fileUuids, String code) {
        fileService.confirmFiles(fileUuids, code);
    }

    @Override
    public List<FileDto> findFilesByUUIDs(List<String> fileList) {
        List<FileDto> fileDtos = fileService.findFilesByUUIDs(fileList);
        return fileDtos;
    }

    @Override
    public FileDto findFilesByUUID(String fileUuid) {
        return fileService.findFileByUUID(fileUuid);
    }

    @Override
    public String fileDownloadUrlByUUID(String fileUUID) {
        return fileService.fileDownloadUrlByUUID(fileUUID);
    }

    @Override
    public void confirmFile(String uuid, String fileCode) {
        fileService.confirmFile(uuid,fileCode);
    }

}
