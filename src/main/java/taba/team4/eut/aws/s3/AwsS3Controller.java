package taba.team4.eut.aws.s3;

import taba.team4.eut.common.code.ResultCode;
import taba.team4.eut.common.controller.BaseController;
import taba.team4.eut.common.exception.BizException;
import taba.team4.eut.common.user.UserInfo;
import taba.team4.eut.common.user.UserRole;
import taba.team4.eut.common.utils.ResponseEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Slf4j
@Controller
@RequestMapping("/s3")
@RequiredArgsConstructor
public class AwsS3Controller extends BaseController {

    private final AwsS3Service awsS3Service;

    private final String BASE_UPLOAD_DIR = "upload";

    @PostMapping("/resource")
    public ResponseEntity upload(@ModelAttribute("userInfo") UserInfo userInfo, @RequestPart("file") MultipartFile multipartFile) throws IOException {
        //log.debug("userInfo : {} ", userInfo);
        if (!userInfo.isLogin()) {
            throw new BizException(ResultCode.HTTP_403.setErrMsg("저장 권한이 없습니다."));
        }

        LocalDate now = LocalDate.now();
        String today = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String key = BASE_UPLOAD_DIR + "/" + today + "/" + userInfo.getId();

        return ResponseEntityUtil.ok(awsS3Service.upload(multipartFile, key).toMap());
    }

    @DeleteMapping("/resource")
    public ResponseEntity remove(@ModelAttribute("userInfo") UserInfo userInfo, AwsS3Dto awsS3) {
        //log.debug("userInfo : {} ", userInfo);

        // 관리자 권한이 있거나 본인이 올린 파일인 경우만 삭제
        if ( userInfo.hasRole(UserRole.SYS_ADMIN)
                || userInfo.hasRole(UserRole.ADMIN)
                || awsS3.getKey().contains(userInfo.getId())) {
            awsS3Service.remove(awsS3);
            return ResponseEntityUtil.ok();
        }

        log.warn("삭제 권한 없음. - userId : {},  s3 : {} ", userInfo.getId(), awsS3);
        return ResponseEntityUtil.fail(ResultCode.HTTP_403.setErrMsg("삭제 권한이 없습니다."));
    }
}
