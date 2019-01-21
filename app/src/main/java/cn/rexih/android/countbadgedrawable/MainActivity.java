package cn.rexih.android.countbadgedrawable;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import cn.rexih.android.badge.CountBadgeDrawable;


public class MainActivity extends AppCompatActivity {

    private ImageView iv_home_cart;
    private CountBadgeDrawable badgeDrawable;

    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_home_cart = findViewById(R.id.iv_home_cart);


        Drawable badge = ResourcesCompat.getDrawable(getResources(), R.drawable.badge_stroke_white, null);
        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_cart_float_2, null);

        badgeDrawable = new CountBadgeDrawable(this, icon, badge, 17f / 40);
        badgeDrawable.setRatioOffset(5f/40,5f/40);
        badgeDrawable.attachToImageView(iv_home_cart);
        iv_home_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                badgeDrawable.setAlert(String.valueOf(count));
            }
        });
        findViewById(R.id.btn_home_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badgeDrawable.unbindBadge();
            }
        });

    }
}
