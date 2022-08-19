package uz.davrbankautoelon.davrbank.service.auth.request.physical;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.davrbankautoelon.davrbank.controller.image.ImageController;
import uz.davrbankautoelon.davrbank.controller.request.physical.GetClientInfoController;
import uz.davrbankautoelon.davrbank.controller.request.physical.SendRequestKATMController;
import uz.davrbankautoelon.davrbank.controller.sms.SMSController;
import uz.davrbankautoelon.davrbank.dto.auth.*;
import uz.davrbankautoelon.davrbank.dto.auth.physical.ImageDto;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalDto;
import uz.davrbankautoelon.davrbank.dto.auth.physical.PhysicalViewDto;
import uz.davrbankautoelon.davrbank.dto.client.ClientInfoDto;
import uz.davrbankautoelon.davrbank.dto.client.GetInfoDto;
import uz.davrbankautoelon.davrbank.dto.katm.RegistrationRequestDto;
import uz.davrbankautoelon.davrbank.dto.katm.RejectRequestDto;
import uz.davrbankautoelon.davrbank.dto.katm.RequestBodyDto;
import uz.davrbankautoelon.davrbank.dto.katm.RequestHeaderDto;
import uz.davrbankautoelon.davrbank.enums.SMSEnums;
import uz.davrbankautoelon.davrbank.mapper.auth.physical.PhysicalMapper;
import uz.davrbankautoelon.davrbank.model.auth._Physical;
import uz.davrbankautoelon.davrbank.repository.IPhysicalRepository;
import uz.davrbankautoelon.davrbank.repository.PhysicalRepository;
import uz.davrbankautoelon.davrbank.response.DataDto;
import uz.davrbankautoelon.davrbank.service.IMainService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class PhysicalService implements IMainService<ResponseDto, PhysicalDto> {

    private final IPhysicalRepository repository;
    private final PhysicalMapper mapper;
    private final SMSController controller;
    private final PhysicalRepository secondRepository;
    private final GetClientInfoController clientInfoController;
    private final SendRequestKATMController sendRequestKATMController;
    private final ImageController imageController;

    public PhysicalService(IPhysicalRepository repository, PhysicalMapper mapper, SMSController controller, PhysicalRepository secondRepository, GetClientInfoController clientInfoController, SendRequestKATMController sendRequestKATMController, ImageController imageController) {
        this.repository = repository;
        this.mapper = mapper;
        this.controller = controller;
        this.secondRepository = secondRepository;
        this.clientInfoController = clientInfoController;
        this.sendRequestKATMController = sendRequestKATMController;
        this.imageController = imageController;
    }

    @Override
    public ResponseEntity<DataDto<ResponseDto>> create(PhysicalDto dto) {
        dto.setStatus("N");
        dto.setClient_type("08");

        List<_Physical> all = repository.findAll();
        log.info("Get all requests ");
        long count = all.size() + 25000000;
        dto.setRequestId(String.valueOf(count));

        ResponseDto x = new ResponseDto();
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode("04501");
        errorDto.setName("Fail during validation");
        x.setError(errorDto);
        x.setMessage(null);


        try {

            ResponseDto dto1 = new ResponseDto();
            dto1.setError(null);
            dto1.setMessage("Successfully created");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDateTime now = LocalDateTime.now();
            //
            GetInfoDto getInfoDto = new GetInfoDto();
            getInfoDto.setNumber(dto.getPassportNumber());
            getInfoDto.setSerial(dto.getSerialNumber());
            getInfoDto.setDate(dto.getBirthDate());
            log.info("Get client info form NIBBD");
            ClientInfoDto info = clientInfoController.info(getInfoDto);
            dto.setFirst_name(info.getGivenname());
            dto.setLast_name(info.getSurname());
            dto.setPatronymic(info.getPatronym());
            log.info(info.toString());
            //
            log.info("Preparing KATM request 1");
            RequestHeaderDto headerDto = new RequestHeaderDto();
            headerDto.setCode(dto.getBranch());
            headerDto.setType("B");
            //
            RequestBodyDto bodyDto = new RequestBodyDto();
            if (
                            info.getDomicile_address() == null || info.getDomicile_address().equals("") ||
                            info.getDomicile_region() == null || info.getDomicile_region().equals("") ||
                            info.getDomicile_district() == null || info.getDomicile_district().equals("") ||
                            info.getPassport_number() == null || info.getPassport_number().equals("") ||
                            info.getSex() == null || info.getSex().equals("") ||
                            info.getPassport_seria() == null || info.getPassport_seria().equals("") ||
                            info.getSubject_state() == null || info.getSubject_state().equals("") ||
                            info.getDate_issue() == null || info.getDate_issue().equals("") ||
                            info.getBirth_date() == null || info.getBirth_date().equals("") ||
                            info.getGivenname() == null || info.getGivenname().equals("") ||
                            info.getSurname() == null || info.getSurname().equals("")
            ) {
                return new ResponseEntity<>(new DataDto<>(x, false), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            bodyDto.setInn("123456789");
            bodyDto.setClaim_number(info.getPassport_number());
            bodyDto.setAgreement_number(info.getInn());
            bodyDto.setClaim_id(String.valueOf(count));
            bodyDto.setClaim_date(dtf.format(now));
            bodyDto.setAgreement_date(dtf.format(now));
            bodyDto.setResident(info.getSubject_state());
            bodyDto.setDocument_type("6");
            bodyDto.setDocument_serial(info.getPassport_seria());
            bodyDto.setDocument_number(info.getPassport_number());
            bodyDto.setDocument_date(info.getDate_issue());
            bodyDto.setGender(info.getSex());
            bodyDto.setBirth_date(info.getBirth_date());
            bodyDto.setDocument_region(info.getDomicile_region());
            bodyDto.setDocument_district(info.getDomicile_district());
            bodyDto.setRegistration_region(info.getDomicile_region());
            bodyDto.setRegistration_district(info.getDomicile_district());
            bodyDto.setNibbd("99181138");
            bodyDto.setName(info.getGivenname());
            bodyDto.setClient_type("08");
            bodyDto.setFamily_name(info.getSurname());
            bodyDto.setPatronymic(info.getPatronym());
            if (info.getDomicile_address().length() > 99) {
                String substring = info.getDomicile_address().substring(99);
                bodyDto.setRegistration_address(substring);
            } else {
                bodyDto.setRegistration_address(info.getDomicile_address());
            }
            bodyDto.setPhone(dto.getPhoneNumber());
            bodyDto.setPin(info.getPin());
            bodyDto.setCredit_type("25");
            bodyDto.setSumma(dto.getSumma());
            bodyDto.setKatm_sir("");
            bodyDto.setProcent("30.00");
            bodyDto.setCredit_duration("36.00");
            bodyDto.setCredit_exemtion("");
            bodyDto.setCurrency("000");
            bodyDto.setLive_address(info.getDomicile_address());
            bodyDto.setLive_cadastr("000123456789123456789");
            bodyDto.setRegistration_cadastr("000123456789123456789");
            //
            RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto();
            registrationRequestDto.setHeader(headerDto);
            registrationRequestDto.setRequest(bodyDto);
            log.info("Send prepared KATM request to KATM");
            sendRequestKATMController.sendRequest(registrationRequestDto, String.valueOf(count));
            Object post = sendRequestKATMController.post(registrationRequestDto);
            log.info("Get Response");
            log.info(post.toString());
            log.info("Update requestID in table");
            Object update = sendRequestKATMController.update(post.toString(), String.valueOf(count));
            System.out.println(update.toString());
            log.info("Save request to Table ");
            repository.save(mapper.fromDto(dto));
            log.info("Send SMS to client for your request is revieved");
            Object o = controller.callSMS(dto.getPhoneNumber(), SMSEnums.REQUEST.getText());
            dto1.setSmsMessage(o);

            return new ResponseEntity<>(new DataDto<>(dto1, true), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Error in creation" + e.getMessage());
            return new ResponseEntity<>(new DataDto<>(x, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<DataDto<ResponseDto>> valid(String phoneNumber, String requestId, String branch) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            log.info("Start rejection");
            log.info("Change Status in table");
            String requestID = sendRequestKATMController.getRequestID(requestId);
            secondRepository.reject(requestId, null);
            log.info("Call KATM rejection");
            log.info("Prepare request reject KATM");
            RejectRequestDto dto = new RejectRequestDto();
            log.info("Send rejection to KATM");
            log.info(dto.toString());
            Object o1 = rejectRequest(dto);
            log.info(o1.toString());
            Object o2 = sendRequestKATMController.rejectUpdate(o1.toString(), requestId);
            log.info("Get Result" + o2.toString());

            secondRepository.valid(requestId);
            ResponseDto dto1 = new ResponseDto();
            dto1.setError(null);
            dto1.setMessage("Request is validated");
            Object o = controller.callSMS(phoneNumber, SMSEnums.RESPONSE.getText());
            dto1.setSmsMessage(o);
            return new ResponseEntity<>(new DataDto<>(dto1, true), HttpStatus.OK);
        } catch (Exception e) {
            ResponseDto dto1 = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("04501");
            errorDto.setName("Fail during validation");
            dto1.setError(errorDto);
            dto1.setMessage(null);
            return new ResponseEntity<>(new DataDto<>(dto1, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataDto<ResponseDto>> reject(String phoneNumber, String requestId, String branch, String message) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            log.info("Start rejection");
            log.info("Change Status in table");
            String requestID = sendRequestKATMController.getRequestID(requestId);
            secondRepository.reject(requestId, message);
            log.info("Call KATM rejection");
            log.info("Prepare request reject KATM");
            RejectRequestDto dto = new RejectRequestDto();
            log.info("Send rejection to KATM");
            log.info(dto.toString());
            Object o1 = rejectRequest(dto);
            Object o2 = sendRequestKATMController.rejectUpdate(o1.toString(), requestId);
            log.info("Get Result");
            ResponseDto dto1 = new ResponseDto();
            dto1.setError(null);
            dto1.setMessage("Request is rejected : " + o2.toString());
            Object o = controller.callSMS(phoneNumber, SMSEnums.REJECT.getText());
            dto1.setSmsMessage(o);
            return new ResponseEntity<>(new DataDto<>(dto1, true), HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            ResponseDto dto1 = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("04502");
            errorDto.setName("Fail during rejection");
            dto1.setError(errorDto);
            dto1.setMessage(null);
            return new ResponseEntity<>(new DataDto<>(dto1, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataDto<?>> getById(String requestId) {
        try {
            PhysicalViewDto request = secondRepository.getRequest(requestId);
            List<ImageDto> images = secondRepository.getImages(requestId);
            request.setImages(images);
            return new ResponseEntity<>(new DataDto<>(request, true), HttpStatus.OK);
        } catch (Exception e) {
            ResponseDto dto1 = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("04503");
            errorDto.setName("Fail by get ID");
            dto1.setError(errorDto);
            dto1.setMessage(null);
            return new ResponseEntity<>(new DataDto<>(dto1, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataDto<?>> getAll() {
        try {
            List<PhysicalViewDto> all = secondRepository.getAll();
            all.forEach(p -> {
                List<ImageDto> images = secondRepository.getImages(p.getId());
                p.setImages(images);
                try {
                    p.setImage_path(imageController.downloadFile(p.getImage_path()));
                } catch (Exception e){
                    log.info("Error whil getting image");
                }
            });
            return new ResponseEntity<>(new DataDto<>(all, true), HttpStatus.OK);
        } catch (Exception e) {
            ResponseDto dto1 = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("04504");
            errorDto.setName("Fail during rejection");
            dto1.setError(errorDto);
            dto1.setMessage(null);
            return new ResponseEntity<>(new DataDto<>(dto1, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataDto<?>> getAllByBranch(String branch) {
        try {
            List<PhysicalViewDto> all = secondRepository.getAllByBranch(branch);
            all.forEach(p -> {
                List<ImageDto> images = secondRepository.getImages(p.getId());
                p.setImages(images);
                try {
                    p.setImage_path(imageController.downloadFile(p.getImage_path()));
                } catch (Exception e){
                    log.info("Error whil getting image");
                }
            });
            return new ResponseEntity<>(new DataDto<>(all, true), HttpStatus.OK);
        } catch (Exception e) {
            ResponseDto dto1 = new ResponseDto();
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("04504");
            errorDto.setName("Fail during rejection");
            dto1.setError(errorDto);
            dto1.setMessage(null);
            return new ResponseEntity<>(new DataDto<>(dto1, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DataDto<List<PhysicalViewDto>>> search(SearchDto dto) {
        List<PhysicalViewDto> search = secondRepository.search(dto);
        return new ResponseEntity<>(new DataDto<>(search, true), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<PhysicalViewDto>>> searchByBranch(SearchDto dto) {
        List<PhysicalViewDto> physicalViewDto = secondRepository.searchByBranch(dto);
        return new ResponseEntity<>(new DataDto<>(physicalViewDto, true), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<List<PhysicalViewDto>>> getByBranch(String branch) {
        List<PhysicalViewDto> byBranch = secondRepository.getByBranch(branch);
        return new ResponseEntity<>(new DataDto<>(byBranch, true), HttpStatus.OK);
    }


    public Object rejectRequest(RejectRequestDto dto) {
        return sendRequestKATMController.reject(dto);
    }
}
