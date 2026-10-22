package parameta.demo.parameta.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TypeDocumentDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	@NotNull(message = "El ID del tipo de documento es obligatorio")
	private Long id;
	
	@NotBlank(message = "El código del documento es obligatorio")
	@Size(max = 3, message = "El código debe tener como máximo 3 caracteres")
	private String code;

	@NotBlank(message = "El nombre del documento es obligatorio")
	@Size(max = 30, message = "El nombre del documento debe tener como máximo 30 caracteres")
	private String document;
}
