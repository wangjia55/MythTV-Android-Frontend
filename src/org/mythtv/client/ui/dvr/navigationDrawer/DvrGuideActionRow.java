/**
 * 
 */
package org.mythtv.client.ui.dvr.navigationDrawer;

import org.mythtv.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * @author dmfrey
 *
 */
public class DvrGuideActionRow extends DvrActionRow {

	private static final String FRAGMENT = "org.mythtv.client.ui.dvr.GuideFragment";
	
	public DvrGuideActionRow( Context context, int actionResId ) {
		super( context, actionResId, true );
	}

	@Override
	public View getView(View convertView) {
		View view = super.getView(convertView);
		
		ImageView imgView = (ImageView)view.findViewById(R.id.navigation_drawer_action_icon);
		imgView.setImageResource(R.drawable.ic_guide_default);
		
		return view;
	}
	
	/* (non-Javadoc)
	 * @see org.mythtv.client.ui.NavigationDrawerActivity.Row#getViewType()
	 */
	@Override
	public int getViewType() {
		return DvrRowType.GUIDE_ROW.ordinal();
	}

	/* (non-Javadoc)
	 * @see org.mythtv.client.ui.navigationDrawer.ActionRow#getFragment()
	 */
	@Override
    public String getFragment() {
		return FRAGMENT;
	}
	
}
