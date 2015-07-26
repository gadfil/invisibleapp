package sys.android.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import sys.android.app.R;
import sys.android.app.model.User;
import sys.android.app.ui.activity.CommentActivity;

/**
 * Created by gadfil on 06.05.2015.
 */
public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<User> users;
    private Context context;
    private View.OnClickListener onClickListener;
    public UserAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;

        try {
            onClickListener = (View.OnClickListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement View.OnClickListener.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        final UserViewHolder userViewHolder = new UserViewHolder(view);
        userViewHolder.cardView.setOnClickListener(onClickListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserViewHolder userHolder = (UserViewHolder) holder;
        Picasso.with(context)
                .load(users.get(position).getLogo())
                .into(userHolder.avatar);
        userHolder.tvDescription.setText(users.get(position).getDescription());
        userHolder.tvLogin.setText(users.get(position).getLogin());
        userHolder.cardView.setTag(users.get(position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

//    @Override
//    public void onClick(View v) {
////        Intent intent = new Intent(context, CommentActivity.class);
////        intent.putExtra(CommentActivity.EXTRA_EMAIL, ((User) v.getTag()).getEmail());
////        Log.d("log", "email " + ((User) v.getTag()).getEmail());
////        int[] startingLocation = new int[2];
////        v.getLocationOnScreen(startingLocation);
////        intent.putExtra(CommentActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
////        CommentActivity.launch(context, intent);
////        context.overridePendingTransition(0, 0);
//    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.card_view)
        CardView cardView;
        @InjectView(R.id.avatar)
        CircleImageView avatar;
        @InjectView(R.id.tvDescription)
        TextView tvDescription;
        @InjectView(R.id.tvLogin)
        TextView tvLogin;


        public UserViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

