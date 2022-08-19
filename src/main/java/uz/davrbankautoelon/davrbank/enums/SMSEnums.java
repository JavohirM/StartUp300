package uz.davrbankautoelon.davrbank.enums;

public enum SMSEnums {
    REQUEST("Ваша заявка отправлена на рассмотрение, ожидайте уведомление по SMS. \n Davr Bank ! \n Для информации 1284."),
    RESPONSE("Ваша заявка одобрена.  Ожидайте звонка"),
    REJECT("К сожалению вам отказано в выдаче кредита, повторно вы можете запросить кредит через 15 дней");

    /*TEXT("Dilshod kamroq telefon o'yna");*/
    public String text;


    SMSEnums(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
