/**
 * This file is part of MythTV Android Frontend
 *
 * MythTV Android Frontend is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MythTV Android Frontend is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MythTV Android Frontend.  If not, see <http://www.gnu.org/licenses/>.
 *
 * This software can be found at <https://github.com/MythTV-Clients/MythTV-Android-Frontend/>
 */
package org.mythtv.service;

import org.mythtv.client.MainApplication;
import org.mythtv.service.util.FileHelper;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author Daniel Frey
 *
 */
public abstract class MythtvService extends IntentService {

	protected static final String TAG = MythtvService.class.getSimpleName();
	
	public static final String FILENAME_EXT = ".json";
    
	protected FileHelper mFileHelper;
    protected MainApplication mMainApplication;
	

	public MythtvService( String name ) {
		super( name );
		
		mFileHelper = new FileHelper( this );

	}

	/* (non-Javadoc)
	 * @see android.app.IntentService#onHandleIntent(android.content.Intent)
	 */
	@Override
	protected void onHandleIntent( Intent intent ) {
		mMainApplication = (MainApplication) MythtvService.this.getApplicationContext();
	}

	protected boolean isBackendConnected() {
		Log.v( TAG, "isBackendConnected : enter" );

		try {
			mMainApplication.getMythServicesApi().mythOperations().getHostName();
			
			Log.v( TAG, "isBackendConnected : exit" );
			return true;
		} catch( Exception e ) {
			Log.w( TAG, "isBackendConnected : error, connecting to backend", e );
		}
		
		Log.v( TAG, "isBackendConnected : exit, backend is offline" );
		return false;
	}
	
}
