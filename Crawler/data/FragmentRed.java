74
https://raw.githubusercontent.com/harshalbenake/hbworkspace1-100/master/ViewPagerDemo/src/pl/looksok/viewpagerdemo/FragmentRed.java
package pl.looksok.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRed extends Fragment {
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_red, container, false);
	return view;
}
}
