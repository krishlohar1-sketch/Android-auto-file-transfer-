package com.example.autofiletransfer

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val navy=Color.rgb(16,26,58); private val purple=Color.rgb(108,76,241); private val cyan=Color.rgb(0,194,255)
    private val bg=Color.rgb(245,247,255); private val text=Color.rgb(23,33,59); private val muted=Color.rgb(105,115,140); private val pink=Color.rgb(236,72,153); private val green=Color.rgb(16,185,129)
    private fun dp(v:Int)= (v*resources.displayMetrics.density).toInt()
    private fun round(color:Int, radius:Int=20): GradientDrawable = GradientDrawable().apply { setColor(color); cornerRadius=dp(radius).toFloat() }
    private fun tv(s:String,size:Float,bold:Boolean=false,color:Int=text): TextView = TextView(this).apply { text=s; textSize=size; setTextColor(color); if(bold) setTypeface(typeface,1); setPadding(dp(2),dp(2),dp(2),dp(2)) }
    private fun button(s:String,color:Int,action:()->Unit): TextView = tv(s,15f,true,Color.WHITE).apply { gravity=Gravity.CENTER; background=round(color,16); setPadding(dp(12),dp(13),dp(12),dp(13)); setOnClickListener{action()} }
    override fun onCreate(b:Bundle?) { super.onCreate(b); showHome() }
    private fun showHome(){
        val scroll=ScrollView(this); val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg);setPadding(dp(18),dp(12),dp(18),dp(28))}; scroll.addView(root)
        val head=LinearLayout(this).apply{gravity=Gravity.CENTER_VERTICAL}; val logo=TextView(this).apply{ text="⚡";textSize=28f;gravity=Gravity.CENTER;background=round(navy,50);setTextColor(Color.WHITE);layoutParams=LinearLayout.LayoutParams(dp(58),dp(58))}; head.addView(logo)
        val title=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;padding(dp(12),0,0,0)}; title.addView(tv("Android Auto File Transfer",21f,true)); title.addView(tv("Fast • Simple • Friendly",12f,false,muted)); head.addView(title,LinearLayout.LayoutParams(0,-2,1f)); root.addView(head,LinearLayout.LayoutParams(-1,dp(72)))
        val hero=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;padding(dp(20),dp(22),dp(20),dp(20));background=GradientDrawable(GradientDrawable.Orientation.TL_BR,intArrayOf(navy,purple,cyan)).apply{cornerRadius=dp(25).toFloat()}};
        hero.addView(tv("Share files. No confusion.",24f,true,Color.WHITE)); hero.addView(tv("Connect two Android phones and move your photos, videos and files quickly.",14f,false,Color.WHITE));
        val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER_VERTICAL}; row.addView(button("📤 Send Files",pink){Toast.makeText(this,"Select files to send",Toast.LENGTH_SHORT).show()},LinearLayout.LayoutParams(0,dp(52),1f)); val sp=Space(this);row.addView(sp,LinearLayout.LayoutParams(dp(12),1));row.addView(button("📥 Receive",green){Toast.makeText(this,"Ready to receive files",Toast.LENGTH_SHORT).show()},LinearLayout.LayoutParams(0,dp(52),1f)); hero.addView(row,LinearLayout.LayoutParams(-1,dp(70)));root.addView(hero,LinearLayout.LayoutParams(-1,dp(250)))
        root.addView(tv("How it works",19f,true).apply{setPadding(0,dp(24),0,dp(10))})
        val steps= arrayOf("1  Connect phones" to "Bring both phones close and start pairing.","2  Choose files" to "Pick photos, videos or documents to share.","3  Transfer" to "Watch the progress and keep both phones nearby.")
        for((a,c) in steps){ val card=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(16),dp(14),dp(16),dp(14));background=round(Color.WHITE,18);elevation=dp(2).toFloat()};card.addView(tv(a,16f,true));card.addView(tv(c,13f,false,muted));root.addView(card,LinearLayout.LayoutParams(-1,dp(82)).apply{bottomMargin=dp(10)}) }
        val about=button("ℹ  About this app",navy){showAbout()};root.addView(about,LinearLayout.LayoutParams(-1,dp(52)).apply{topMargin=dp(10)})
        val share=button("↗  Share App",cyan){shareApp()};root.addView(share,LinearLayout.LayoutParams(-1,dp(52)).apply{topMargin=dp(10)})
        setContentView(scroll)
    }
    private fun showAbout(){
        val box=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(24),dp(22),dp(24),dp(18));background=round(Color.WHITE,26)}
        box.addView(tv("⚡ Android Auto File Transfer",22f,true)); box.addView(tv("\nA friendly Android file-transfer app designed to make sharing photos, videos and documents simple. The app is built around fast local device-to-device transfer and an easy pairing experience.\n\nFeatures\n• Simple Send & Receive interface\n• Fast local transfer architecture\n• Camera/visual pairing support\n• Transfer progress support\n• Clean, colourful and beginner-friendly design\n\nDeveloped by Krish Lohar\n📞 9635465352",14f,false,muted))
        box.addView(button("↗  Share App",cyan){shareApp()},LinearLayout.LayoutParams(-1,dp(48)).apply{topMargin=dp(12)})
        box.addView(button("Close",purple){(box.parent as? android.view.ViewGroup)?.removeView(box)},LinearLayout.LayoutParams(-1,dp(48)).apply{topMargin=dp(12)})
        val overlay=FrameLayout(this).apply{setBackgroundColor(0x99000000.toInt())};overlay.addView(box,FrameLayout.LayoutParams(-1,-2,Gravity.CENTER).apply{leftMargin=dp(20);rightMargin=dp(20)});setContentView(overlay)
    }
    private fun shareApp(){
        val intent=android.content.Intent(android.content.Intent.ACTION_SEND).apply{
            type="text/plain"
            putExtra(android.content.Intent.EXTRA_SUBJECT,"Android Auto File Transfer")
            putExtra(android.content.Intent.EXTRA_TEXT,"Android Auto File Transfer — fast and simple file sharing app.")
        }
        startActivity(android.content.Intent.createChooser(intent,"Share Android Auto File Transfer"))
    }
}
