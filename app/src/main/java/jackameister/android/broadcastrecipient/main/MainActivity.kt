package jackameister.android.broadcastrecipient.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jackameister.android.broadcastrecipient.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wasStartedFromBroadcast =
                (intent?.action == ConduitBroadcastReceiver.ACTION_START_FROM_BROADCAST)
        if (wasStartedFromBroadcast) {
            val receivedIntent: Intent =
                    intent.extras!![ConduitBroadcastReceiver.EXTRA_RECEIVED_INTENT] as Intent
            tvMain.text = describeIntent(receivedIntent)
        }
    }

    private fun describeIntent(intent: Intent): String = buildString {
        append("Received a broadcast intent.\n\n")
        append("Action: ${intent.action}\n")
        append("Categories: ${intent.categories}\n")
        append("Type: ${intent.type}\n")
        append("Data: ${intent.dataString}\n\n")
        append(describeExtras(intent))
    }

    private fun describeExtras(intent: Intent): String = buildString {
        if (intent.extras == null) {
            append("Null extras.")
        } else {
            val extras: Bundle = intent.extras!!
            append("${extras.size()} extra(s).\n")
            if (extras.containsKey(EXTRA_WRAPPED_INTENT)) {
                val wrappedIntent = extras[EXTRA_WRAPPED_INTENT] as Intent
                append("One extra is an intent wrapped in Conduit's format.\n")
                append("Wrapped intent:\n$wrappedIntent")
            }
        }
    }

    companion object {
        private const val EXTRA_WRAPPED_INTENT = "jackameister.android.conduit.extra.intent"
    }
}
