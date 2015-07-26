package sys.android.app.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import sys.android.app.DataUtil;
import sys.android.app.R;
import sys.android.app.facke.FakeUsers;
import sys.android.app.model.Comment;
import sys.android.app.model.User;
import sys.android.app.ui.adapter.CommentsAdapter;
import sys.android.app.ui.view.SendCommentButton;

public class CommentActivity extends BaseActivity implements SendCommentButton.OnSendClickListener {
    public static final String EXTRA_EMAIL = "EXTRA_EMAIL";
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";
    private static final int CAMERA_RESULT = 0;

    @InjectView(R.id.contentRoot)
    LinearLayout contentRoot;
    @InjectView(R.id.rvComments)
    RecyclerView rvComments;
    @InjectView(R.id.llAddComment)
    LinearLayout llAddComment;
    @InjectView(R.id.etComment)
    EditText etComment;
    @InjectView(R.id.btnSendComment)
    SendCommentButton btnSendComment;
    @InjectView(R.id.btPhoto)
    ImageButton btPhoto;

    private CommentsAdapter commentsAdapter;
    private int drawingStartLocation;

    User frend;

    private  int imgCount = 0;
    private Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        toolbar.setNavigationIcon(R.drawable.ic_action_hardware_keyboard_backspace);

        btPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etComment.getText().toString().indexOf(Comment.PHOTO_TAG)==-1) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_RESULT);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(CommentActivity.this, new Intent(CommentActivity.this, UsersActivity.class));
            }
        });

        Log.e("log", " @ " + getIntent().getStringExtra(EXTRA_EMAIL));
        frend = new FakeUsers().getUserByEmail(getIntent().getStringExtra(EXTRA_EMAIL));
//        frend = new FakeUsers().getUserByEmail(DataUtil.getSettingValue(this, DataUtil.EMAIL));
        setTitle(frend.getLogin());
        Log.d("log", " " + frend);
        setupComments();
        setupSendCommentButton();

        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT && data!=null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            ImageSpan imageSpan = new ImageSpan(this, thumbnail);

            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(etComment.getText());

            // this is a string that will let you find a place, where the ImageSpan is.
//            String imgId = "[img="+imgCount+"]";
//            images.put("[img="+imgCount+"]", thumbnail);
            photo=thumbnail;
            imgCount++;

            int selStart = etComment.getSelectionStart();

            // current selection is replace? with imageId
            builder.replace(etComment.getSelectionStart(), etComment.getSelectionEnd(), Comment.PHOTO_TAG);
            // this "replaces" imageId string with image span. If you do builder.toString() - the string will contain imageIs where the imageSpan is.
            // you can yse this later - if you want to location of imageSpan in text;
            builder.setSpan(imageSpan, selStart, selStart + Comment.PHOTO_TAG.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        }
    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);
        ArrayList<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setComment("Hello, My Friend!\n" +
                " How are you today? I miss you a lot today!.");
        comment.setUser(frend);
        comments.add(comment);
        comment = new Comment();
        comment.setComment("We need to meet and discuss that business offer!");
        comment.setUser(frend);
        comments.add(comment);

        commentsAdapter = new CommentsAdapter(this, frend, comments);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    commentsAdapter.setAnimationsLocked(true);
                }
            }
        });

        Timer myTimer = new Timer(); // ??????? ??????
        final Handler uiHandler = new Handler();

        myTimer.schedule(new TimerTask() { // ?????????? ??????
            @Override
            public void run() {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commentsAdapter.removeCommentIffDeed();
                    }
                });
            }


        }, 0L, 1000);
    }

    private void setupSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    private void startIntroAnimation() {
        ViewCompat.setElevation(toolbar, 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        llAddComment.setTranslationY(200);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(toolbar, DataUtil.dpToPx(8));
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        commentsAdapter.updateItems();
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(toolbar, 0);
        contentRoot.animate()
                .translationY(DataUtil.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CommentActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {

            User iAm = new FakeUsers().getUserByEmail(DataUtil.getSettingValue(this, DataUtil.EMAIL));
            Comment comment = new Comment();
            comment.setUser(iAm);
            comment.setComment(etComment.getText().toString());
            comment.setPhoto(photo);
            commentsAdapter.addItem(comment);
            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);
            Log.d("log", "commentsAdapter " + commentsAdapter);
            Log.d("log", "rvComments " + rvComments);
            Log.d("log", "rvComments.getChildCount " + rvComments.getChildCount());

            if (+rvComments.getChildCount() > 0) {
                rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());
            }
            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);

            imgCount = 0;
            photo = null;

            hideSoftKeyboard(this);
        }
    }

     void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    private boolean validateComment() {
        if (TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }

}
