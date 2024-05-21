package taba.team4.eut.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;  // 버킷 이름

    private final AmazonS3 amazonS3;

    /**
     * S3 파일 업로드
     */
    public AwsS3Dto upload(MultipartFile multipartFile, String dirName) throws IOException {

        String key = dirName + "/" + UUID.randomUUID() + multipartFile.getOriginalFilename();

        // set ObjectMatadata
        byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        objectMetadata.setContentType(multipartFile.getContentType());

        // save in S3
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        this.amazonS3.putObject(new PutObjectRequest(this.bucket, key, byteArrayInputStream,objectMetadata));
        // this.amazonS3.putObject(new PutObjectRequest(this.bucket, key, byteArrayInputStream,objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        byteArrayInputStream.close();

        String path = amazonS3.getUrl(bucket, key).toString();

        log.debug("# key = {}, path = {} ", key, path);

        return AwsS3Dto
                .builder()
                .key(key)
                .path(path)
                .build();
    }

    /**
     * S3 파일 삭제
     */
    public void remove(AwsS3Dto AwsS3Dto) {
        if (!amazonS3.doesObjectExist(bucket, AwsS3Dto.getKey())) {
            throw new AmazonS3Exception("Object " +AwsS3Dto.getKey()+ " does not exist!");
        }
        amazonS3.deleteObject(bucket, AwsS3Dto.getKey());
    }

    // upload original image file
    public void uploadFile(MultipartFile image, String s3Path) {
        log.debug("# IN AwsS3DtoService.uploadFile - path : {}", s3Path);
        try {
            // set ObjectMatadata
            byte[] bytes = IOUtils.toByteArray(image.getInputStream());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(bytes.length);
            objectMetadata.setContentType(image.getContentType());

            // save in S3
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            this.amazonS3.putObject(this.bucket, s3Path.replace(File.separatorChar, '/'), byteArrayInputStream, objectMetadata);

            byteArrayInputStream.close();


        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    // upload thumbnail image file
//    public void uploadThumbFile(MultipartFile image, String thumbs3Path) {
//        log.debug("# IN AwsS3DtoService.uploadThumbFile - path : {}", thumbs3Path);
//
//        try {
//            // make thumbnail image for s3
//            BufferedImage bufferImage = ImageIO.read(image.getInputStream());
//            BufferedImage thumbnailImage = Thumbnails.of(bufferImage).size(400, 333).asBufferedImage();
//
//            ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
//            String imageType = image.getContentType();
//            ImageIO.write(thumbnailImage, imageType.substring(imageType.indexOf("/")+1), thumbOutput);
//
//            // set metadata
//            ObjectMetadata thumbObjectMetadata = new ObjectMetadata();
//            byte[] thumbBytes = thumbOutput.toByteArray();
//            thumbObjectMetadata.setContentLength(thumbBytes.length);
//            thumbObjectMetadata.setContentType(image.getContentType());
//
//            // save in s3
//            InputStream thumbInput = new ByteArrayInputStream(thumbBytes);
//            this.amazonS3.putObject(this.bucket, thumbs3Path.replace(File.separatorChar, '/'), thumbInput, thumbObjectMetadata);
//            //this.amazonS3.putObject(new PutObjectRequest(this.bucket, key, byteArrayInputStream,objectMetadata));
//
//            thumbInput.close();
//            thumbOutput.close();
//        }
//        catch(Exception e){
//            log.error(e.getMessage());
//        }
//    }
}
