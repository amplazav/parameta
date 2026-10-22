package parameta.demo.parameta.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import parameta.demo.parameta.util.DateUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	@NotNull(message = "El cargo es obligatorio")
	@Valid
	private RoleDTO roleDTO;
	
	@NotNull(message = "El salario es obligatorio")
	@Positive(message = "El salario debe ser mayor que cero")
	private Double salary;
	
	@NotNull(message = "La fecha de vinculación es obligatoria")
	@PastOrPresent(message = "La fecha de vinculación no puede estar en el futuro")
	private LocalDate dateVinculation;	
	
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String timeVinculation;

	public EmployeeDTO(Long id, RoleDTO roleDTO, Double salary, LocalDate dateVinculation) {
		this.id = id;
		this.roleDTO = roleDTO;
		this.salary = salary;
		this.dateVinculation = dateVinculation;
		calcularDatosDerivados();
	}
    
    public void calcularDatosDerivados() {
        if (dateVinculation != null) {            
            this.timeVinculation = DateUtils.calculateVinculationTime(dateVinculation);
        }
    }

}
