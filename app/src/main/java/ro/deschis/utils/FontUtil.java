package ro.deschis.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class FontUtil {
    private static Typeface appFontRegular, appFontBold, appFontItalic, appFontBoldItalic;

    private static void initTypefaces(Context context) {
        if (appFontRegular == null) {
            appFontRegular = Typeface.createFromAsset(context.getAssets(), "PT_Sans-Web-Regular.ttf");
        }
        if (appFontBold == null) {
            appFontBold = Typeface.createFromAsset(context.getAssets(), "PT_Sans-Web-Bold.ttf");
        }
        if (appFontItalic == null) {
            appFontItalic = Typeface.createFromAsset(context.getAssets(), "PT_Sans-Web-Italic.ttf");
        }
        if (appFontBoldItalic == null) {
            appFontBoldItalic = Typeface.createFromAsset(context.getAssets(), "PT_Sans-Web-BoldItalic.ttf");
        }
    }

    public static void setFonts(Activity activity) {
        final View parentActivityView = activity.getWindow().getDecorView();
        parentActivityView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setFonts(parentActivityView);
                parentActivityView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public static void setFonts(Dialog dialog) {
        setFonts(dialog.getWindow().getDecorView());
    }

    public static void setFonts(Fragment fragment) {
        setFonts(fragment.getView());
    }

    public static void setFonts(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            setStyle(textView, textView.getTypeface() != null ? textView.getTypeface().getStyle() : Typeface.NORMAL);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setFonts(viewGroup.getChildAt(i));
            }
        }
    }

    public static void setStyle(TextView textView, int style) {
        initTypefaces(textView.getContext());
        switch (style) {
            case Typeface.BOLD_ITALIC:
                textView.setTypeface(appFontBoldItalic);
                break;
            case Typeface.ITALIC:
                textView.setTypeface(appFontItalic);
                break;
            case Typeface.BOLD:
                textView.setTypeface(appFontBold);
                break;
            default:
                textView.setTypeface(appFontRegular);
        }
    }

    public static void setTextColour(View view, int textColour) {
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(textColour);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setTextColour(viewGroup.getChildAt(i), textColour);
            }
        }
    }
}
