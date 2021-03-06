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
package org.mythtv.db.dvr;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.client.ui.preferences.LocationProfile;
import org.mythtv.db.channel.model.ChannelInfo;
import org.mythtv.db.dvr.model.Program;
import org.mythtv.db.guide.model.ProgramGuide;

import android.content.ContentUris;
import android.content.Context;

/**
 * @author Daniel Frey
 *
 */
public class ProgramGuideDaoHelper extends ProgramDaoHelper {

//	private static final String TAG = ProgramGuideDaoHelper.class.getSimpleName();
	
	private static ProgramGuideDaoHelper singleton = null;

	/**
	 * Returns the one and only ProgramGuideDaoHelper. init() must be called before 
	 * any 
	 * 
	 * @return
	 */
	public static ProgramGuideDaoHelper getInstance() {
		if( null == singleton ) {

			synchronized( ProgramGuideDaoHelper.class ) {

				if( null == singleton ) {
					singleton = new ProgramGuideDaoHelper();
				}
			
			}

		}
		
		return singleton;
	}
	
	private ProgramGuideDaoHelper() {
		super();
	}

	public ProgramGuide getProgramGuideForDate( final Context context, final LocationProfile locationProfile, DateTime date ) {
//		Log.d( TAG, "getProgramGuideForDate : enter" );

		ProgramGuide guide = new ProgramGuide();
		
		List<ChannelInfo> channels = mChannelDaoHelper.findAll( context, locationProfile );
		
//		for( ChannelInfo channel : channels ) {
//			List<Program> programs = findAll( context, locationProfile, channel.getChannelId(), date );
//			
//			channel.setPrograms( programs );
//		}
		guide.setChannels( channels );
		
//		Log.d( TAG, "getProgramGuideForDate : enter" );
		return guide;
	}
	
	public List<Program> findAll( final Context context, final LocationProfile locationProfile, int channelId, DateTime date ) {
//		Log.d( TAG, "findAll : enter" );
		
		DateTime start = date.withZone( DateTimeZone.UTC );
		
		String[] projection = new String[] { ProgramConstants._ID, ProgramConstants.FIELD_TITLE, ProgramConstants.FIELD_SUB_TITLE, ProgramConstants.FIELD_CATEGORY, ProgramConstants.FIELD_START_TIME, ProgramConstants.FIELD_END_TIME };
		String selection = ProgramConstants.TABLE_NAME_GUIDE + "." + ProgramConstants.FIELD_CHANNEL_ID + " = ? AND " + ProgramConstants.TABLE_NAME_GUIDE + "." + ProgramConstants.FIELD_END_TIME + " >= ? AND " + ProgramConstants.TABLE_NAME_GUIDE + "." + ProgramConstants.FIELD_START_TIME + " < ?";
		String[] selectionArgs = new String[] { String.valueOf( channelId ), String.valueOf( start.getMillis() ), String.valueOf( start.plusDays( 1 ).getMillis() ) };
		String sortOrder = ProgramConstants.TABLE_NAME_GUIDE + "." + ProgramConstants.FIELD_START_TIME;
		
		selection = appendLocationHostname( context, locationProfile, selection, ProgramConstants.TABLE_NAME_GUIDE );

//		for( String p : projection ) {
//			Log.v( TAG, "projection=" + p );
//		}
//		Log.v( TAG, "selection=" + selection );
//		for( String arg : selectionArgs ) {
//			Log.v( TAG, "arg=" + arg );
//		}
//		Log.v( TAG, "sortOrder=" + sortOrder );
		
		List<Program> programs = findAll( context, ProgramConstants.CONTENT_URI_GUIDE, projection, selection, selectionArgs, sortOrder, ProgramConstants.TABLE_NAME_GUIDE );
		
//		Log.d( TAG, "findAll : exit" );
		return programs;
	}

	/* (non-Javadoc)
	 * @see org.mythtv.db.dvr.ProgramDaoHelper#findAll()
	 */
	@Override
	public List<Program> findAll( final Context context, final LocationProfile locationProfile ) {
//		Log.d( TAG, "findAll : enter" );
		
		String selection = appendLocationHostname( context, locationProfile, "", ProgramConstants.TABLE_NAME_GUIDE );

		List<Program> programs = findAll( context, ProgramConstants.CONTENT_URI_GUIDE, null, selection, null, null, ProgramConstants.TABLE_NAME_GUIDE );
		
//		Log.d( TAG, "findAll : exit" );
		return programs;
	}

	/**
	 * @param title
	 * @return
	 */
	public List<Program> findAllByTitle( final Context context, final LocationProfile locationProfile, final String title ) {
//		Log.d( TAG, "findAllByTitle : enter" );
		
		String selection = ProgramConstants.FIELD_TITLE + " = ?";
		String[] selectionArgs = new String[] { title };

		selection = appendLocationHostname( context, locationProfile, selection, ProgramConstants.TABLE_NAME_GUIDE );
		
		List<Program> programs = findAll( context, ProgramConstants.CONTENT_URI_GUIDE, null, selection, selectionArgs, null, ProgramConstants.TABLE_NAME_GUIDE );
//		if( null != programs && !programs.isEmpty() ) {
//			for( Program program : programs ) {
//				Log.v( TAG, "findAllByTitle : channelId=" + program.getChannelInfo().getChannelId() + ", startTime=" + program.getStartTime().getMillis() + ", program=" + program.toString() );
//			}
//		}
		
//		Log.d( TAG, "findAllByTitle : exit" );
		return programs;
	}

	/**
	 * @param id
	 * @return
	 */
	public Program findOne( final Context context, final Long id ) {
//		Log.d( TAG, "findOne : enter" );
//		Log.d( TAG, "findOne : id=" + id );
		
		Program program = findOne( context, ContentUris.withAppendedId( ProgramConstants.CONTENT_URI_GUIDE, id ), null, null, null, null, ProgramConstants.TABLE_NAME_GUIDE );
//		if( null != program ) {
//			Log.d( TAG, "findOne : program=" + program.toString() );
//		}
		
//		Log.d( TAG, "findOne : exit" );
		return program;
	}

	/* (non-Javadoc)
	 * @see org.mythtv.db.dvr.ProgramDaoHelper#findOne(int, org.joda.time.DateTime)
	 */
	@Override
	public Program findOne( final Context context, final LocationProfile locationProfile, final int channelId, final DateTime startTime ) {
//		Log.d( TAG, "findOne : enter" );
		
		String selection = ProgramConstants.TABLE_NAME_GUIDE + "." + ProgramConstants.FIELD_CHANNEL_ID + " = ? AND " + ProgramConstants.TABLE_NAME_GUIDE + "." + ProgramConstants.FIELD_START_TIME + " = ?";
		String[] selectionArgs = new String[] { String.valueOf( channelId ), String.valueOf( startTime.getMillis() ) };

		selection = appendLocationHostname( context, locationProfile, selection, ProgramConstants.TABLE_NAME_GUIDE );
		
		Program program = findOne( context, ProgramConstants.CONTENT_URI_GUIDE, null, selection, selectionArgs, null, ProgramConstants.TABLE_NAME_GUIDE );
//		if( null != program ) {
//			Log.v( TAG, "findOne : program=" + program.toString() );
//		} else {
//			Log.v( TAG, "findOne : program not found!" );
//		}
		
//		Log.d( TAG, "findOne : exit" );
		return program;
	}

	/* (non-Javadoc)
	 * @see org.mythtv.db.dvr.ProgramDaoHelper#save(org.mythtv.services.api.dvr.Program)
	 */
	@Override
	public int save( final Context context, final LocationProfile locationProfile, Program program ) {
//		Log.d( TAG, "save : enter" );

		int saved = save( context, ProgramConstants.CONTENT_URI_GUIDE, locationProfile, program, ProgramConstants.TABLE_NAME_GUIDE );
		
//		Log.d( TAG, "save : exit" );
		return saved;
	}

	/* (non-Javadoc)
	 * @see org.mythtv.db.dvr.ProgramDaoHelper#deleteAll()
	 */
	@Override
	public int deleteAll( final Context context ) {
//		Log.d( TAG, "deleteAll : enter" );

		int deleted = deleteAll( context, ProgramConstants.CONTENT_URI_GUIDE );
		
//		Log.d( TAG, "deleteAll : exit" );
		return deleted;
	}

	/* (non-Javadoc)
	 * @see org.mythtv.db.dvr.ProgramDaoHelper#delete(org.mythtv.services.api.dvr.Program)
	 */
	@Override
	public int delete( final Context context, final LocationProfile locationProfile, Program program ) {
//		Log.d( TAG, "delete : enter" );

		int deleted = delete( context, ProgramConstants.CONTENT_URI_GUIDE, locationProfile, program, ProgramConstants.TABLE_NAME_GUIDE );
		
//		Log.d( TAG, "delete : exit" );
		return deleted;
	}

}
