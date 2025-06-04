package icu.baolong.social.repository.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * IP信息（用户表中 ip_info 字段对应的实体类）
 *
 * @author Silas Yan 2025-05-26 22:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpInfo implements Serializable {

	/**
	 * 注册时的IP
	 */
	private String registerIp;

	/**
	 * 注册时的IP详细信息
	 */
	private IpDetail registerIpDetail;

	/**
	 * 最后登录的IP
	 */
	private String lastLoginIp;

	/**
	 * 最后登录的IP详细信息
	 */
	private IpDetail lastLoginIpDetail;

	/**
	 * IP详细信息
	 */
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IpDetail implements Serializable {
		/**
		 * 本次的IP
		 */
		private String ip;
		/**
		 * 本次的IP的运营商
		 */
		private String isp;
		/**
		 * 本次的IP的运营商ID
		 */
		private String ispId;
		/**
		 * 本次的IP的所在城市
		 */
		private String city;
		/**
		 * 本次的IP的所在城市ID
		 */
		private String cityId;
		/**
		 * 本次的IP的所在国家
		 */
		private String country;
		/**
		 * 本次的IP的所在国家ID
		 */
		private String countryId;
		/**
		 * 本次的IP的所在省份
		 */
		private String region;
		/**
		 * 本次的IP的所在省份ID
		 */
		private String regionId;

		@Serial
		private static final long serialVersionUID = 1L;
	}

	@Serial
	private static final long serialVersionUID = 1L;
}
