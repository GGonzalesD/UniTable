package com.unitable.unitableprojectupc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JoinResponse {
	private Long usuario_id;
	private Long grupo_id;
}
