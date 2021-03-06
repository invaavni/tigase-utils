/*
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
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

import java.util.Calendar;

/**
 * This is too slow.
 * <br>
 * <br>
 * Created: Tue Oct 28 21:08:58 2008
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public abstract class TimeUtils {

	public static int getHourNow() {
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinuteNow() {
		Calendar cal = Calendar.getInstance();

		return cal.get(Calendar.MINUTE);
	}
}

