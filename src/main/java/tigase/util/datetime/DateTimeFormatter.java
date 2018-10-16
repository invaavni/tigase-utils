/*
 * DateTimeFormatter.java
 *
 * Tigase Jabber/XMPP Utils
 * Copyright (C) 2004-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.util.datetime;

import tigase.annotations.TigaseDeprecated;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bmalkow
 * @deprecated use {@linkplain TimestampHelper} instead.
 */
@Deprecated
@TigaseDeprecated(since = "4.0.0", removeIn = "4.1.0")
public class DateTimeFormatter {

	private static final String DATE = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)";

	private static final String TIME = "(\\d\\d):(\\d\\d):(\\d\\d)(.\\d+)?";

	private static final String TIME_ZONE = "(([+-]\\d\\d:\\d\\d)|Z)";
	private final SimpleDateFormat TIMESTAMP_FORMATTER_WITH_MS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX");
	private final DateFormat dateFormat;
	private final Pattern datePattern;
	private final DateFormat dateTimeFormatUTC;
	private final Pattern dateTimePattern;
	private final DateFormat timeFormatUTC;
	private final Pattern timePattern;
	private final TimeZone timeZoneUTC = TimeZone.getTimeZone("UTC");

	public DateTimeFormatter() {
		this.dateTimePattern = Pattern.compile("^" + DATE + "T" + TIME + TIME_ZONE + "$");
		this.datePattern = Pattern.compile("^" + DATE + "$");
		this.timePattern = Pattern.compile("^" + TIME + TIME_ZONE + "?$");
		this.dateTimeFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		this.dateTimeFormatUTC.setTimeZone(timeZoneUTC);

		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.dateFormat.setTimeZone(timeZoneUTC);

		this.timeFormatUTC = new SimpleDateFormat("HH:mm:ss'Z'");
		this.timeFormatUTC.setTimeZone(timeZoneUTC);
		TIMESTAMP_FORMATTER_WITH_MS.setTimeZone(timeZoneUTC);
	}

	public String formatDate(final Date date) {
		return dateFormat.format(date);
	}

	public String formatDateTime(final Date date) {
		return dateTimeFormatUTC.format(date);
	}

	public String formatInLegacyDelayedDelivery(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTimeZone(TimeZone.getTimeZone("GMT"));
		now.setTime(date);
		return String.format("%1$tY%1$tm%1$tdT%1$tH:%1$tM:%1$tS", now);
	}

	public String formatTime(final Date date) {
		return timeFormatUTC.format(date);
	}

	public String formatWithMs(Date date) {
		synchronized (TIMESTAMP_FORMATTER_WITH_MS) {
			return TIMESTAMP_FORMATTER_WITH_MS.format(date);
		}
	}

	public Calendar parseDateTime(final String value) throws ParseException {
		if (value == null || value.isEmpty()) {
			return null;
		}
		Matcher m = dateTimePattern.matcher(value);

		if (m.find()) {
			int yyyy = Integer.valueOf(m.group(1));
			int MM = Integer.valueOf(m.group(2));
			int dd = Integer.valueOf(m.group(3));
			int hh = Integer.valueOf(m.group(4));
			int mm = Integer.valueOf(m.group(5));
			int ss = Integer.valueOf(m.group(6));
			String ms = m.group(7);
			String tzValue = m.group(8);
			TimeZone tz;
			if (tzValue.equals("Z")) {
				tz = timeZoneUTC;
			} else {
				tz = TimeZone.getTimeZone("GMT" + tzValue);
			}
			Calendar calendar = Calendar.getInstance(tz);
			calendar.clear();
			calendar.set(yyyy, MM - 1, dd, hh, mm, ss);
			if (ms != null) {
				calendar.set(Calendar.MILLISECOND, Integer.valueOf(ms.substring(1)));
			}
			return calendar;
		}

		m = datePattern.matcher(value);
		if (m.find()) {
			int yyyy = Integer.valueOf(m.group(1));
			int MM = Integer.valueOf(m.group(2));
			int dd = Integer.valueOf(m.group(3));

			Calendar calendar = Calendar.getInstance(timeZoneUTC);
			calendar.clear();
			calendar.set(yyyy, MM - 1, dd);
			return calendar;
		}

		m = timePattern.matcher(value);
		if (m.find()) {
			int hh = Integer.valueOf(m.group(1));
			int mm = Integer.valueOf(m.group(2));
			int ss = Integer.valueOf(m.group(3));
			String ms = m.group(4);
			String tzValue = m.group(5);
			TimeZone tz;
			if (tzValue == null || tzValue.equals("Z")) {
				tz = timeZoneUTC;
			} else {
				tz = TimeZone.getTimeZone("GMT" + tzValue);
			}
			Calendar calendar = Calendar.getInstance(tz);
			calendar.clear();
			calendar.set(Calendar.SECOND, ss);
			calendar.set(Calendar.MINUTE, mm);
			calendar.set(Calendar.HOUR_OF_DAY, hh);
			if (ms != null) {
				calendar.set(Calendar.MILLISECOND, Integer.valueOf(ms.substring(1)));
			}
			return calendar;
		}

		throw new ParseException("Can't parse datetime, date or time: " + value, -1);
	}
}