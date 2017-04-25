package com.example.sqisoft.moldcreateapp.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqisoft.moldcreateapp.manager.DataManager;
import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.util.FragmentUtil;
import com.example.sqisoft.moldcreateapp.view.ColorPickerGridViewAdapter;
import com.example.sqisoft.moldcreateapp.view.DrawingView;
import com.example.sqisoft.moldcreateapp.view.HeaderGridView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDrawing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDrawing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDrawing extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mFragmentDrawingView;
    private Button mWaitingButton;

    private ImageButton img;
    private Button mUndoButton;
    private boolean isSelected = false;

    private HeaderGridView gridView;
    private ColorPickerGridViewAdapter mColorPickerGridViewAdapter;

    private DrawingView mDrawingView;

    TextView touchPad, textX, textY;
   // private int mSeletedMold = 0;
    private ImageView mSeletedPaletteColorView;

    public FragmentDrawing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDrawing.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDrawing newInstance(String param1, String param2) {
        FragmentDrawing fragment = new FragmentDrawing();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mFragmentDrawingView = inflater.inflate(R.layout.fragment_drawing, container, false);
        attachViewAndListener();
        // Inflate the layout for this fragment
        FragmentUtil.trace();

        DataManager.getInstance().setmSeletecPaletteColorView(mSeletedPaletteColorView);

        addBaseAdapter();

        DataManager.getInstance().setmDrawingView(mDrawingView);

        return mFragmentDrawingView;
    }


    private void addBaseAdapter(){
        gridView = (HeaderGridView) mFragmentDrawingView.findViewById(R.id.colorpicker_list);
        mColorPickerGridViewAdapter = new ColorPickerGridViewAdapter(this,getContext());
        gridView.setAdapter(mColorPickerGridViewAdapter);
    }


    private void attachViewAndListener(){
        mWaitingButton = (Button) mFragmentDrawingView.findViewById(R.id.waiting_mold_button);
        mWaitingButton.setOnClickListener(mWaitingButtonistener);
        mDrawingView = (DrawingView) mFragmentDrawingView.findViewById(R.id.drawing_view);
        mSeletedPaletteColorView = (ImageView) mFragmentDrawingView.findViewById(R.id.selected_palette_color);

        mUndoButton = (Button) mFragmentDrawingView.findViewById(R.id.undo_button);
        mUndoButton.setOnClickListener(mUndoButtonListener);

        switch (DataManager.getInstance().getSeletedMoldIndex()){

            case  1 :
             mDrawingView.setBackgroundResource(R.drawable.mold_01b);
            break;

            case  2 :
             mDrawingView.setBackgroundResource(R.drawable.mold_02b);
                break;

            case  3 :
              mDrawingView.setBackgroundResource(R.drawable.mold_03b);
                break;

            case  4 :
              mDrawingView.setBackgroundResource(R.drawable.mold_04b);
                break;


        }

    }
    private File filepath,externalFilePath;

    private Button.OnClickListener mWaitingButtonistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            mDrawingView.setDrawingCacheEnabled(true);
            Bitmap b = mDrawingView.getDrawingCache();
        //    DataManager.getInstance().setmCapturingBitmap(b.copy(b.getConfig(),true));
            FragmentUtil.addFragment(new FragmentSending(b.copy(b.getConfig(),true) ));
        }
    };

    private Button.OnClickListener mUndoButtonListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

       mDrawingView.eraser();
            //mDrawingView.onClickUndo();

        }
    };




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
