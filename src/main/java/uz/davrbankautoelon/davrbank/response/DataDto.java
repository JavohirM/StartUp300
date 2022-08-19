package uz.davrbankautoelon.davrbank.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class DataDto<T> implements Serializable {

    protected T data;
    protected boolean success;


    public DataDto(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

}
