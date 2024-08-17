package id.backend.todo_list.service;

import id.backend.todo_list.entity.ActivityEntity;
import id.backend.todo_list.model.GeneralResponseDto;
import id.backend.todo_list.model.request.ActivityRequestDto;
import id.backend.todo_list.repository.ActivityRepository;
import id.backend.todo_list.utils.JsonUtils;
import id.backend.todo_list.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public GeneralResponseDto doSaveActivity(ActivityRequestDto inMsg) {
        GeneralResponseDto outMsg = new GeneralResponseDto();
        try {
            ActivityEntity activityData = new ActivityEntity();
            activityData.setName(inMsg.getName());
            activityData.setDescription(inMsg.getDescription());
            // Parse tanggal mulai dan selesai menggunakan TimeUtils dari format string -> Date
            Date startDate = TimeUtils.doGetDateFormat(inMsg.getStartDate());
            Date endDate = TimeUtils.doGetDateFormat(inMsg.getEndDate());
            activityData.setStartDate(startDate);
            activityData.setEndDate(endDate);
            // Save data ke database
            ActivityEntity savedActivity = activityRepository.save(activityData);
            // Cek apakah data berhasil disimpan
            if (savedActivity == null) {
                outMsg.setResponseCode(HttpStatusCode.valueOf(400).toString());
                outMsg.setResponseMessage("Gagal menambah aktivitas, data kosong");
                return outMsg;
            }
            outMsg.setResponseCode(HttpStatusCode.valueOf(200).toString());
            outMsg.setResponseMessage("Berhasil menambah aktivitas");
            outMsg.setResponseData(savedActivity);
            return outMsg;
        } catch (Exception e) {
            outMsg.setResponseCode(HttpStatusCode.valueOf(500).toString());
            outMsg.setResponseMessage("Internal Server Error");
            log.info("RESPONSE ERROR: [{}]", JsonUtils.convertToString(outMsg));
            log.info("err: [{}]", JsonUtils.convertToString(e));
            return outMsg;
        }
    }
}
