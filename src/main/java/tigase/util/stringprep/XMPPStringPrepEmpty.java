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
package tigase.util.stringprep;

/**
 * Class implementing stringprep processor interface. This is a dummy implementation performing no processing at all.
 * All methods simply return value passed as the method call parameter. Use of this implementation is recommended inly
 * in strictly controlled systems where there is no possibility of getting incorrectly formated JIDs to the system. Of
 * course this implementation causes no impact on the system performance.
 * <br>
 * Created: Feb 4, 2010 9:52:41 AM
 *
 * @author <a href="mailto:artur.hefczyc@tigase.org">Artur Hefczyc</a>
 * @version $Rev$
 */
public class XMPPStringPrepEmpty
		implements XMPPStringPrepIfc {

	@Override
	public String nameprep(String domain) {
		return domain;
	}

	@Override
	public String nodeprep(String localpart) {
		return localpart;
	}

	@Override
	public String resourceprep(String resource) {
		return resource;
	}
}

