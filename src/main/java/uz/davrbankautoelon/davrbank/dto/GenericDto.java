package uz.davrbankautoelon.davrbank.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericDto implements Dto, Serializable {
    private Long id;
}
