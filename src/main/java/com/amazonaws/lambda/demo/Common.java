/**
 *
 */
package com.amazonaws.lambda.demo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * 共通処理を提供するクラス
 * @author
 *
 */
public class Common {

	/**
	 * unixtimeから指定された時間単位で切り捨てし返却
	 * @param targetTime 変換対象日時
	 * @param second 丸め時間（秒）
	 * @return roundhalfUpTime 丸め処理後unixtime
	 *
	 */
	public static long roundDownForUnixtime(long targetTime, int roundSecond) {
		// 端数算出
		long fraction = targetTime % roundSecond;
		long roundDownTime = 0;

		// 端数の切り捨てを行う
		roundDownTime = targetTime - fraction;

		return roundDownTime;
	}

	/**
	 * 現在時刻から1時間前の59分のUnixtimeを返却
	 * @return epochSecond 現在時刻から1時間前のUnixtime
	 *
	 */
	public static long getUnixtimeHourAgo59min() {
		// 端数算出
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.minusHours(1);
		long min = ldt.getMinute();

		min = 59 - min;

		ldt = ldt.plusMinutes(min);

		ZonedDateTime zdt = ldt.atZone(ZoneOffset.ofHours(+9));

		long epochSecond = zdt.toEpochSecond();
		return epochSecond;
	}

	/**
	 * 現在時刻のUnixtimeを返却
	 * @return epochSecond 現在時刻のUnixtime
	 *
	 */
	public static long getUnixtimeNow() {
		LocalDateTime ldt = LocalDateTime.now();

		ZonedDateTime zdt = ldt.atZone(ZoneOffset.ofHours(+9));

		long epochSecond = zdt.toEpochSecond();
		return epochSecond;
	}

}
