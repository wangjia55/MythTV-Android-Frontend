/**
 *  This file is part of MythTV for Android
 * 
 *  MythTV for Android is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MythTV for Android is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MythTV for Android.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * This software can be found at <https://github.com/MythTV-Android/MythTV-Service-API/>
 *
 */
package org.mythtv.db.myth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Daniel Frey
 *
 */
public class SettingList {

	@JsonProperty( "SettingList" )
	private Setting settings;
	
	public SettingList() { }

	/**
	 * @return the setting
	 */
	public Setting getSetting() {
		return settings;
	}

	/**
	 * @param setting the setting to set
	 */
	public void setSetting( Setting settings ) {
		this.settings = settings;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append( "SettingList {" );
		
		if( settings != null ) {
			builder.append( "settings=" );
			builder.append( settings );
		}
		
		builder.append( "}" );
	
		return builder.toString();
	}
	
}
