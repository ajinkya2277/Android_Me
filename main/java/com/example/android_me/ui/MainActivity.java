package com.example.android_me.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android_me.R;
import com.example.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    private boolean mTwoPane;
    private Button nextButton;
    private GridView gridView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = findViewById(R.id.next_button);
        gridView = findViewById(R.id.images_grid_view);
        if (findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            nextButton.setVisibility(View.GONE);
            gridView.setNumColumns(2);
            if (savedInstanceState != null) {
                BodyPartFragment headFragment = new BodyPartFragment();

                headFragment.setmImageIds(AndroidImageAssets.getHeads());
                headFragment.setmListIndex(1);
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                BodyPartFragment bodyFragment = new BodyPartFragment();
                headFragment.setmImageIds(AndroidImageAssets.getBodies());
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                BodyPartFragment legFragment = new BodyPartFragment();
                headFragment.setmImageIds(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }

        }else {
            mTwoPane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        Toast.makeText(this, "Position clicked ="+position, Toast.LENGTH_SHORT).show();

        int bodyPartNumber = position / 12;

        int listIndex = position - 12 * bodyPartNumber;

        if (mTwoPane){
            BodyPartFragment newFragment = new BodyPartFragment();
            switch (bodyPartNumber) {
                case 0:
                newFragment.setmImageIds(AndroidImageAssets.getHeads());
                newFragment.setmListIndex(listIndex);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.head_container, newFragment)
                        .commit();
            }
        }else {
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;

            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt("headIndex",headIndex);
        bundle.putInt("bodyIndex",bodyIndex);
        bundle.putInt("legIndex",legIndex);

        final Intent intent = new Intent(this,AndroidMeActivity.class);
        intent.putExtras(bundle);
        //


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
