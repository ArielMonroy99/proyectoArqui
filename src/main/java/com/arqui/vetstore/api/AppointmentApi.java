package com.arqui.vetstore.api;

import com.arqui.vetstore.bl.AppointmentBl;
import com.arqui.vetstore.dto.AppointmentDto;
import com.arqui.vetstore.dto.CancelationEntity;
import com.arqui.vetstore.dto.ScheduleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/appointment")
public class AppointmentApi {

    private final AppointmentBl appointmentBl;
    public static final Logger logger = LoggerFactory.getLogger(AppointmentApi.class);
    @Autowired
    public AppointmentApi(AppointmentBl appointmentBl) {
        this.appointmentBl = appointmentBl;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<ScheduleEntity> getAvaliableSchedule(@RequestParam Integer vetId, @RequestParam Date startDate,  @RequestParam Date endDate){
        logger.info("vetId {}, startDate {} , endDate {}",vetId,startDate,endDate);
        return  appointmentBl.getAvailableSchedules(vetId,  startDate,  endDate);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public AppointmentDto saveAppointment(@RequestBody AppointmentDto appointmentDto){
        logger.info("appointmentDto {}",appointmentDto);
        return appointmentBl.saveAppointmet(appointmentDto);
    }
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public Page<AppointmentDto> getAppointmentsByUser(@PathVariable Integer userId, @RequestParam Integer page, @RequestParam Integer size){
        logger.info("userId {}",userId);
        return appointmentBl.getAppointmentsByUser(userId,page,size);
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public String cancelAppointment(@RequestBody CancelationEntity cancelationEntity){
        logger.info("appointmentDto {}",cancelationEntity);
        return appointmentBl.cancelAppointment(cancelationEntity);
    }
}
