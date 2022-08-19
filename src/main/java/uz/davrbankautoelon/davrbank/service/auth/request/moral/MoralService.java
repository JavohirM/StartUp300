package uz.davrbankautoelon.davrbank.service.auth.request.moral;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.davrbankautoelon.davrbank.controller.image.ImageController;
import uz.davrbankautoelon.davrbank.controller.sms.SMSController;
import uz.davrbankautoelon.davrbank.dto.auth.ErrorDto;
import uz.davrbankautoelon.davrbank.dto.auth.ResponseDto;
import uz.davrbankautoelon.davrbank.dto.auth.moral.*;
import uz.davrbankautoelon.davrbank.enums.SMSEnums;
import uz.davrbankautoelon.davrbank.mapper.auth.moral.MoralKATMMapper;
import uz.davrbankautoelon.davrbank.mapper.auth.moral.MoralMapper;
import uz.davrbankautoelon.davrbank.model.auth._Moral;
import uz.davrbankautoelon.davrbank.repository.IMoralRepository;
import uz.davrbankautoelon.davrbank.repository.KATMRepository;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.IMainService;
import uz.davrbankautoelon.davrbank.service.auth.client.MoralClientInfo;
import uz.davrbankautoelon.davrbank.service.auth.request.physical.SendRequestToKATMService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MoralService implements IMainService<ResponseDto, MoralDto> {


    private final MoralMapper mapper;
    private final IMoralRepository repository;
    private final ImageController controller;
    private final MoralKATMMapper toKTM;
    private final MoralClientInfo info;
    private final SendRequestToKATMService service;
    private final SMSController smsController;
    private final KATMRepository katmRepository;

    public MoralService(MoralMapper mapper, IMoralRepository repository, ImageController controller, MoralKATMMapper toKTM, MoralClientInfo info, SendRequestToKATMService service, SMSController smsController, KATMRepository katmRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.controller = controller;
        this.toKTM = toKTM;
        this.info = info;
        this.service = service;
        this.smsController = smsController;
        this.katmRepository = katmRepository;
    }

    @Override
    public ResponseEntity<DataDto<ResponseDto>> create(MoralDto dto) {


        List<_Moral> all = repository.findAll();
        long requestId = 45000000 + all.size();

        dto.setRequestId(String.valueOf(requestId));
        dto.setStatus("N");
        dto.setClient_type("09");
        log.info("Call create method");
        ResponseDto responseDto = new ResponseDto();
        ErrorDto errorDto = new ErrorDto();
        log.info("Upload images");
        try {
            List<MoralImagesDto> collect = dto.getImages().stream()
                    .peek(p -> {
                        String certificate = controller.uploadFile(p.getCertificate()).getFileName();
                        String document2 = controller.uploadFile(p.getDocument2()).getFileName();
                        String passport = controller.uploadFile(p.getPassport()).getFileName();
                        String file = controller.uploadPdf(p.getFile()).getFileName();
                        List<MoralImagesDto.SecondaryImage> collect1 = p.getDocument1().stream().peek(secondaryImage -> {
                            String fileName = controller.uploadFile(secondaryImage.getImagePath()).getFileName();
                            secondaryImage.setImagePath(fileName);
                        }).collect(Collectors.toList());
                        p.setDocument1(collect1);
                        p.setPassport(passport);
                        p.setCertificate(certificate);
                        p.setDocument2(document2);
                        p.setFile(file);
                    }).collect(Collectors.toList());
            dto.setImages(collect);
        } catch (Exception e) {
            log.info("Error during uploading images");
            errorDto.setCode("500");
            errorDto.setName("Error while uploading image or file : " + e.getMessage());
            responseDto.setSmsMessage(null);
            responseDto.setMessage(null);
            responseDto.setError(errorDto);
            return new ResponseEntity<>(new DataDto<>(responseDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Successfully uploaded");


        try {
            MoralInfoDto info = this.info.getInfo(dto.getBranch(), dto.getInn());
            dto.setName(info.getSubject().getName());
            MoralKATMDto ktmDto = toKTM.toKTM(info, dto.getBranch(), dto.getPhone(), dto.getAmount(), String.valueOf(requestId));
            if(ktmDto == null) {
                log.info("KATM Details are null.");
                errorDto.setCode("500");
                errorDto.setName("KATM Details are null.");
                responseDto.setSmsMessage(null);
                responseDto.setMessage(null);
                responseDto.setError(errorDto);
                return new ResponseEntity<>(new DataDto<>(responseDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String s = katmRepository.saveKATMRequest(ktmDto, String.valueOf(requestId));
            log.info(s);
            Object response = service.postMoral(ktmDto);
            Object update = katmRepository.update(response.toString(), String.valueOf(requestId));
            log.info(update.toString());
        } catch (Exception e) {
            log.info("Error in getting client info or sending request to KATM :  " + e.getMessage());
            errorDto.setCode("500");
            errorDto.setName("Error in getting client info or sending request to KATM :  " + e.getMessage());
            responseDto.setSmsMessage(null);
            responseDto.setMessage(null);
            responseDto.setError(errorDto);
            return new ResponseEntity<>(new DataDto<>(responseDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {

            responseDto.setMessage("Created");
            responseDto.setError(null);
            log.info("Send SMS to client for your request is revieved");
            Object o = smsController.callSMS(dto.getPhone(), SMSEnums.REQUEST.getText());
            responseDto.setSmsMessage(o);
            repository.save(mapper.fromDto(dto));
            return new ResponseEntity<>(new DataDto<>(responseDto, true), HttpStatus.CREATED);
        } catch (Exception ex) {
            errorDto.setCode("500");
            errorDto.setName(ex.getMessage());
            responseDto.setSmsMessage(null);
            responseDto.setMessage(null);
            responseDto.setError(errorDto);
            return new ResponseEntity<>(new DataDto<>(responseDto, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
