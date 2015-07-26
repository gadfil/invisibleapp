package sys.android.app.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import sys.android.app.R;
import sys.android.app.model.Comment;
import sys.android.app.model.User;


public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_I_AM= 0;
    public static final int TYPE_FREND = 1;

    private Context context;
    private int itemsCount = 0;
    private int lastAnimatedPosition = -1;
    private int avatarSize;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    ArrayList<Comment> comments;
    private User freand;
    public CommentsAdapter(Context context, User freand, ArrayList<Comment> comments) {
        this.context = context;
        this.freand = freand;
        this.comments = comments;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    @Override
    public int getItemViewType(int position) {
        if(comments.get(position).getUser().getEmail().equals(freand.getEmail())){
            return TYPE_FREND;
        }else{
            return TYPE_I_AM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view;
        if (TYPE_FREND == viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.item_comment1, parent, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        }
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        CommentViewHolder holder = (CommentViewHolder) viewHolder;
//        switch (position % 3) {
//            case 0:
//                holder.tvComment.setText("Hello I miss.");
//                break;
//            case 1:
//                holder.tvComment.setText("Cool rest, it is necessary to repeat, you are super.");
//                break;
//            case 2:
//                holder.tvComment.setText("Are you so interested, let's meet at the motel ;)");
//                break;
//        }







        if(comments.get(position).getPhoto()!=null){
            SpannableStringBuilder ssb = new SpannableStringBuilder( "  " +comments.get(position).getComment().replace(Comment.PHOTO_TAG, "") );
            ssb.setSpan(new ImageSpan(comments.get(position).getPhoto()), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvComment.setText(ssb, TextView.BufferType.SPANNABLE);
        }else {
            holder.tvComment.setText(comments.get(position).getComment());
        }
        Picasso.with(context)
                .load(comments.get(position).getUser().getLogo())
                .centerCrop()
                .resize(avatarSize, avatarSize)

                .into(holder.ivUserAvatar);


////        TextView textView2 = (TextView)findViewById( R.id.TextView2 );
//        SpannableStringBuilder ssb = new SpannableStringBuilder( "Here's a smiley  " );
////        Bitmap smiley = BitmapFactory.decodeResource( getResources(), R.drawable.emoticon );
//        ssb.setSpan(new ImageSpan(smiley), 16, 17, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        textView2.setText(ssb, TextView.BufferType.SPANNABLE);

    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void updateItems() {
//        itemsCount = 10;
        notifyDataSetChanged();
    }

    public void addItem(Comment comment) {
        comments.add(comment);
//        notifyItemInserted(comments.size() - 1);
        notifyDataSetChanged();
    }

    public void removeCommentIffDeed(){
        for(int i = 0; i < comments.size(); i++){
            if((new Date().getTime() - comments.get(i).getDate().getTime()) > 30* 1000){
                comments.remove(i);
                notifyDataSetChanged();
                break;

            }
        }
    }
    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ivUserAvatar)
        CircleImageView ivUserAvatar;
        @InjectView(R.id.tvComment)
        TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
