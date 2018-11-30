package jackameister.android.broadcastrecipient.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Receives intents that have been sent by Conduit (or by BroadcastSender or a third-party app).
 */
class ConduitBroadcastReceiver : BroadcastReceiver() {

    /**
     * Forwards the received intent to [MainActivity]. The "received intent" is the intent that
     * was sent by Conduit (or by BroadcastSender or a third-party app).
     *
     * [MainActivity] sometimes wants to know whether it was started by this [BroadcastReceiver] or
     * whether it was started a different way. To give it the information it needs, we store the
     * received intent in another intent. The new intent has an action of
     * [ACTION_START_FROM_BROADCAST], and it has an extra with key [EXTRA_RECEIVED_INTENT] that
     * stores the received intent. Doing this allows for [MainActivity] to check the action of the
     * intent that started it: if the action is [ACTION_START_FROM_BROADCAST], then [MainActivity]
     * knows that it was started by [ConduitBroadcastReceiver]. If you're developing an app that
     * receives broadcasts from Conduit, you might not need to do this at all. (And don't confuse
     * this process with Conduit's process of wrapping intents before broadcasting them; the two
     * processes are mechanically similar, but they're performed for completely different reasons.)
     */
    override fun onReceive(context: Context, receivedIntent: Intent) {
        val intentToStartActivity = Intent(ACTION_START_FROM_BROADCAST)
        intentToStartActivity.setClassName(context.packageName, MainActivity::class.java.name)
        intentToStartActivity.putExtra(EXTRA_RECEIVED_INTENT, receivedIntent)
        intentToStartActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intentToStartActivity)
    }

    companion object {

        /**
         * This string is NOT part of Conduit's API. It's just used to make this app work right.
         */
        const val ACTION_START_FROM_BROADCAST =
                "jackameister.android.broadcastrecipient.intent.action.startFromBroadcast"

        /**
         * This string is NOT part of Conduit's API. It's just used to make this app work right.
         */
        const val EXTRA_RECEIVED_INTENT =
                "jackameister.android.broadcastrecipient.intent.extra.receivedIntent"
    }
}
