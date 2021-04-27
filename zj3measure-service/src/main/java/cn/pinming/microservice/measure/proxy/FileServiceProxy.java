package cn.pinming.microservice.measure.proxy;

import cn.pinming.v2.common.api.dto.FileDto;
import lombok.NonNull;

import java.util.List;

/**
 * Created by jin on 2020-03-17.
 */
public interface FileServiceProxy {

    String getFileDownLoadUrl(@NonNull String fileUuid);

    void confirmFiles(List<String> fileUuids, String code);

    void confirmFile(String uuid, String fileCode);

    List<FileDto> findFilesByUUIDs(List<String> fileList);

    FileDto findFilesByUUID(@NonNull String fileUuid);

    String fileDownloadUrlByUUID(String fileUUID);
}
