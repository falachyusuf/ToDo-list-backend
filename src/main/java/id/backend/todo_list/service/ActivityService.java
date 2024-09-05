package id.backend.todo_list.service;

import id.backend.todo_list.entity.ActivityEntity;
import id.backend.todo_list.model.GeneralResponseDto;
import id.backend.todo_list.model.request.ActivityRequestDto;
import id.backend.todo_list.repository.ActivityRepository;
import id.backend.todo_list.utils.JsonUtils;
import id.backend.todo_list.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
                outMsg.setResponseCode(HttpStatusCode.valueOf(204).toString());
                outMsg.setResponseMessage("Gagal menambah aktivitas");
                log.info("RESPONSE SAVE ERROR: [{}]", JsonUtils.convertToString(outMsg));
                return outMsg;
            }
            outMsg.setResponseCode(HttpStatusCode.valueOf(200).toString());
            outMsg.setResponseMessage("Berhasil menambah aktivitas");
            outMsg.setResponseData(savedActivity);
            log.info("RESPONSE SAVE SUCCESS: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        } catch (Exception e) {
            outMsg.setResponseCode(HttpStatusCode.valueOf(500).toString());
            outMsg.setResponseMessage("Internal Server Error");
            log.info("RESPONSE SAVE ERROR: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        }
    }

    public GeneralResponseDto doUpdateActivity(Long id, ActivityRequestDto inMsg) {
        GeneralResponseDto outMsg = new GeneralResponseDto();
        try {
            Optional<ActivityEntity> activityData = activityRepository.findById(id);
            // Cek apakah data ditemukan pada database
            if(!activityData.isPresent()){
                outMsg.setResponseCode(HttpStatusCode.valueOf(204).toString());
                outMsg.setResponseMessage("Data tidak ditemukan");
                log.info("RESPONSE UPDATE ERROR: [{}]", JsonUtils.convertToString(outMsg));
                return outMsg;
            }
            activityData.get().setName(inMsg.getName());
            activityData.get().setDescription(inMsg.getDescription());
            // Parse tanggal mulai dan selesai menggunakan TimeUtils dari format string -> Date
            Date startDate = TimeUtils.doGetDateFormat(inMsg.getStartDate());
            Date endDate = TimeUtils.doGetDateFormat(inMsg.getEndDate());
            activityData.get().setStartDate(startDate);
            activityData.get().setEndDate(endDate);
            // Update data ke database
            ActivityEntity updatedActivity = activityRepository.save(activityData.get());
            // Cek apakah data berhasil diupdate
            if (updatedActivity == null) {
                outMsg.setResponseCode(HttpStatusCode.valueOf(204).toString());
                outMsg.setResponseMessage("Gagal mengupdate aktivitas");
                log.info("RESPONSE UPDATE ERROR: [{}]", JsonUtils.convertToString(outMsg));
                return outMsg;
            }
            outMsg.setResponseCode(HttpStatusCode.valueOf(200).toString());
            outMsg.setResponseMessage("Berhasil mengupdate aktivitas");
            outMsg.setResponseData(updatedActivity);
            log.info("RESPONSE UPDATE SUCCESS: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        } catch (Exception e) {
            outMsg.setResponseCode(HttpStatusCode.valueOf(500).toString());
            outMsg.setResponseMessage("Internal Server Error");
            log.info("RESPONSE UPDATE ERROR: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        }
    }

    public GeneralResponseDto doInquiryAllActivity(Integer pageNumber, Integer pageSize) {
        GeneralResponseDto outMsg = new GeneralResponseDto();
        try {
            Sort sort = Sort.by(Sort.Direction.ASC, "id");
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<ActivityEntity> activityData = Page.empty();
            activityData = activityRepository.findAll(pageable);
            // Cek apakah data kosong atau tidak
            if(activityData == null){
                outMsg.setResponseCode(HttpStatusCode.valueOf(204).toString());
                outMsg.setResponseMessage("Data tidak ditemukan");
                outMsg.setResponseData(activityData.getContent());
                log.info("RESPONSE INQUIRY ERROR: [{}]", JsonUtils.convertToString(outMsg));
                return outMsg;
            }
            outMsg.setResponseCode(HttpStatusCode.valueOf(200).toString());
            outMsg.setResponseMessage("Berhasil mendapatkan data aktivitas");
            outMsg.setResponseData(activityData.getContent());
            log.info("RESPONSE INQUIRY SUCCESS: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        } catch (Exception e) {
            outMsg.setResponseCode(HttpStatusCode.valueOf(500).toString());
            outMsg.setResponseMessage("Internal Server Error");
            log.info("RESPONSE INQUIRY ERROR: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        }
    }

    public GeneralResponseDto doInquiryByIdActivity(long id) {
        GeneralResponseDto outMsg = new GeneralResponseDto();
        try {
            Optional<ActivityEntity> activityById = activityRepository.getActivityById(id);
            if(activityById.isEmpty()){
                outMsg.setResponseCode(HttpStatusCode.valueOf(204).toString());
                outMsg.setResponseMessage("Data tidak ditemukan");
                outMsg.setResponseData(null);
                log.info("RESPONSE INQUIRY BY ID ERROR: [{}]", JsonUtils.convertToString(outMsg));
                return outMsg;
            }
            outMsg.setResponseCode(HttpStatusCode.valueOf(200).toString());
            outMsg.setResponseMessage("Berhasil mendapatkan data aktivitas");
            log.info("RESPONSE INQUIRY BY ID SUCCESS: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        }catch (Exception e){
            outMsg.setResponseCode(HttpStatusCode.valueOf(500).toString());
            outMsg.setResponseMessage("Internal Server Error");
            log.info("RESPONSE INQUIRY BY ID ERROR: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        }
    }

    public GeneralResponseDto doDeleteActivity(Long id) {
        GeneralResponseDto outMsg = new GeneralResponseDto();
        try {
            Optional<ActivityEntity> activityData = activityRepository.findById(id);
            if(activityData.isEmpty()) {
                outMsg.setResponseCode(HttpStatusCode.valueOf(204).toString());
                outMsg.setResponseMessage("Data tidak ditemukan");
                outMsg.setResponseData(null);
                log.info("RESPONSE DELETE ERROR: [{}]", JsonUtils.convertToString(outMsg));
                return outMsg;
            }
            activityRepository.deleteById(id);
            outMsg.setResponseCode(HttpStatusCode.valueOf(200).toString());
            outMsg.setResponseMessage("Berhasil menghapus aktivitas");
            log.info("RESPONSE INQUIRY BY ID SUCCESS: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        } catch (Exception e) {
            outMsg.setResponseCode(HttpStatusCode.valueOf(500).toString());
            outMsg.setResponseMessage("Internal Server Error");
            log.info("RESPONSE DELETE ERROR: [{}]", JsonUtils.convertToString(outMsg));
            return outMsg;
        }
    }
}
