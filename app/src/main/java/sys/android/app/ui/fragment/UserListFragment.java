package sys.android.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import sys.android.app.DataUtil;
import sys.android.app.R;
import sys.android.app.facke.FakeUsers;
import sys.android.app.model.User;
import sys.android.app.ui.activity.CommentActivity;
import sys.android.app.ui.adapter.TestRecyclerViewAdapter;
import sys.android.app.ui.adapter.UserAdapter;

/**
 * Created by gadfil on 06.05.2015.
 */
public class UserListFragment extends Fragment {

    private static final String ARG_SIZE = "arg_size";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<User> mContentItems = new ArrayList<>();
    private int size;
    public static UserListFragment newInstance(int size) {
        UserListFragment fragment = new UserListFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_SIZE, size);
        fragment.setArguments(arg);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        size = getArguments().getInt(ARG_SIZE);
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        FakeUsers users = new FakeUsers();

        for (int i = 0; i < size; ++i) {
            for(User u: users.users) {
                if (!DataUtil.getSettingValue(getActivity(), DataUtil.LOGIN).equals(u.getLogin()))
                    mContentItems.add(u);
            }
        }
        mAdapter = new RecyclerViewMaterialAdapter(new UserAdapter(mContentItems, getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

//        Intent intent = new Intent(context, CommentActivity.class);
//        intent.putExtra(CommentActivity.EXTRA_EMAIL, ((User) v.getTag()).getEmail());
//        Log.d("log", "email " + ((User) v.getTag()).getEmail());
//        int[] startingLocation = new int[2];
//        v.getLocationOnScreen(startingLocation);
//        intent.putExtra(CommentActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
//        CommentActivity.launch(context, intent);
//        context.overridePendingTransition(0, 0);
    }
}
