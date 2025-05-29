package icu.baolong.social.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 权限DTO
 *
 * @author Silas Yan 2025-05-29 21:16
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO implements Serializable {

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 角色标识
	 */
	private String roleSign;

	/**
	 * 权限ID
	 */
	private Long permId;

	/**
	 * 权限标识
	 */
	private String permSign;

	/**
	 * 角色ID列表
	 */
	private Set<Long> roleIdList = Set.of();

	/**
	 * 权限ID列表
	 */
	private Set<Long> permIdList = Set.of();

	/**
	 * 角色标识列表
	 */
	private Set<String> roleSignList = Set.of();

	/**
	 * 权限标识列表
	 */
	private Set<String> permSignList = Set.of();

	/**
	 * 角色列表
	 */
	private List<AuthDTO> roleTree;

	/**
	 * 权限列表
	 */
	private List<AuthDTO> permTree;

	@Serial
	private static final long serialVersionUID = 1L;
}
