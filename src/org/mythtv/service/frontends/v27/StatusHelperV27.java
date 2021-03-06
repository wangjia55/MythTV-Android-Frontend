/**
 * 
 */
package org.mythtv.service.frontends.v27;

import java.util.ArrayList;
import java.util.List;

import org.mythtv.client.ui.preferences.LocationProfile;
import org.mythtv.db.AbstractBaseHelper;
import org.mythtv.db.frontends.model.State;
import org.mythtv.db.frontends.model.StateStringItem;
import org.mythtv.db.frontends.model.Status;
import org.mythtv.service.util.NetworkHelper;
import org.mythtv.services.api.ApiVersion;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.connect.MythAccessFactory;
import org.mythtv.services.api.v027.MythServicesTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author Daniel Frey
 *
 */
public class StatusHelperV27 extends AbstractBaseHelper {

	private static final String TAG = StatusHelperV27.class.getSimpleName();
	
	private static final ApiVersion mApiVersion = ApiVersion.v027;
	
	private static MythServicesTemplate mMythServicesTemplate;

	private static StatusHelperV27 singleton;
	
	/**
	 * Returns the one and only StatusHelperV27. init() must be called before 
	 * any 
	 * @return
	 */
	public static StatusHelperV27 getInstance() {
		if( null == singleton ) {
			
			synchronized( StatusHelperV27.class ) {

				if( null == singleton ) {
					singleton = new StatusHelperV27();
				}
			
			}
			
		}
		
		return singleton;
	}
	
	/**
	 * Constructor. No one but getInstance() can do this.
	 */
	private StatusHelperV27() { }

	public Status process( final Context context, final LocationProfile locationProfile, final String url ) {
		Log.v( TAG, "process : enter" );
		
		if( !NetworkHelper.getInstance().isFrontendConnected( context, locationProfile, url ) ) {
			Log.w( TAG, "process : Frontend @ '" + url + "' is unreachable" );
			
			return null;
		}
		
		mMythServicesTemplate = (MythServicesTemplate) MythAccessFactory.getServiceTemplateApiByVersion( mApiVersion, url );
		if( null == mMythServicesTemplate ) {
			Log.w( TAG, "process : Master Backend '" + locationProfile.getHostname() + "' is unreachable" );
			
			return null;
		}

		Status status = null;

		try {

			status = downloadStatus( locationProfile, url );
			
		} catch( Exception e ) {
			Log.e( TAG, "process : error", e );
		
			status = null;
		}

		Log.v( TAG, "process : exit" );
		return status;
	}

	// internal helpers
	
	private Status downloadStatus( final LocationProfile locationProfile, final String url ) throws RemoteException, OperationApplicationException {
		Log.v( TAG, "downloadHosts : enter" );
	
		Status status = null;

		ResponseEntity<org.mythtv.services.api.v027.beans.FrontendStatus> responseEntity = mMythServicesTemplate.frontendOperations().getStatus( ETagInfo.createEmptyETag() );

		if( responseEntity.getStatusCode().equals( HttpStatus.OK ) ) {

			org.mythtv.services.api.v027.beans.FrontendStatus versionStatus = responseEntity.getBody();

			if( null != versionStatus ) {
				status = load( versionStatus );	
			}

		}

		Log.v( TAG, "downloadHosts : exit" );
		return status;
	}
	
	private Status load( org.mythtv.services.api.v027.beans.FrontendStatus versionStatus ) {
		Log.v( TAG, "load : enter" );
		
		Status status = new Status();
		
		if( null != versionStatus.getState() ) {
			
			State state = new State();
			
			if( null != versionStatus.getState() && !versionStatus.getState().isEmpty() ) {
				
				List<StateStringItem> items = new ArrayList<StateStringItem>();
				for( String versionItem : versionStatus.getState().keySet() ) {
					StateStringItem item = new StateStringItem();
					item.setKey( versionItem );
					item.setValue( versionStatus.getState().get( versionItem ) );
					
					items.add( item );
				}
				
				state.setStates( items );
			}
			
			status.setState( state );
		}
		
		Log.v( TAG, "load : exit" );
		return status;
	}

}
