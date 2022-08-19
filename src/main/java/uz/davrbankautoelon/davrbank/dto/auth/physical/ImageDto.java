package uz.davrbankautoelon.davrbank.dto.auth.physical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.davrbankautoelon.davrbank.dto.GenericDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto extends GenericDto {

   private String pathImage;
}
