package dupradosantini.sostoolbackend.domain.dtos;


import dupradosantini.sostoolbackend.domain.BusinessRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleHistoryDto {

    private BusinessRole role;

    private Date DateStart;

    private Date DateEnd;

}
