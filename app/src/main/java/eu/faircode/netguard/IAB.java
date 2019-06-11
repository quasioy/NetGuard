package eu.faircode.netguard;

/*
    This file is part of NetGuard.

    NetGuard is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    NetGuard is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with NetGuard.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2015-2019 by Marcel Bokhorst (M66B)
*/

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class IAB /*implements ServiceConnection */ {
    private static final String TAG = "NetGuard.IAB";

    /*private Context context;
    private Delegate delegate;
    private IInAppBillingService service = null;

    private static final int IAB_VERSION = 3;*/

    public interface Delegate {
        void onReady(IAB iab);
    }

    public IAB(Delegate delegate, Context context) {
       /* this.context = context.getApplicationContext();
        this.delegate = delegate;*/
    }
/*
    public void bind() {
        Log.i(TAG, "Bind");
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        context.bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
    }

    public void unbind() {
        if (service != null) {
            Log.i(TAG, "Unbind");
            context.unbindService(this);
            service = null;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Log.i(TAG, "Connected");
        service = IInAppBillingService.Stub.asInterface(binder);
        delegate.onReady(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(TAG, "Disconnected");
        service = null;
    }

    public void updatePurchases() throws RemoteException {
        return;

        // Get purchases
        List<String> skus = getPurchases();

        SharedPreferences prefs = context.getSharedPreferences("IAB", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (String product : prefs.getAll().keySet())
            if (!ActivityPro.SKU_DONATION.equals(product)) {
                Log.i(TAG, "removing SKU=" + product);
                //editor.remove(product);
            }
        for (String sku : skus) {
            Log.i(TAG, "adding SKU=" + sku);
            editor.putBoolean(sku, true);
        }
        editor.apply();
    }

    public List<String> getPurchases() throws RemoteException {
        // Get purchases
        Bundle bundle = service.getPurchases(IAB_VERSION, context.getPackageName(), "inapp", null);
        Log.i(TAG, "getPurchases");
        Util.logBundle(bundle);
        int response = (bundle == null ? -1 : bundle.getInt("RESPONSE_CODE", -1));
        Log.i(TAG, "Response=" + getResult(response));
        if (response != 0)
            throw new IllegalArgumentException(getResult(response));

        ArrayList<String> details = bundle.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
        return (details == null ? new ArrayList<String>() : details);
    }

    public PendingIntent getBuyIntent(String sku, boolean subscription) throws RemoteException {
        if (service == null)
            return null;
        Bundle bundle = service.getBuyIntent(IAB_VERSION, context.getPackageName(), sku, subscription ? "subs" : "inapp", "netguard");
        Log.i(TAG, "getBuyIntent sku=" + sku + " subscription=" + subscription);
        Util.logBundle(bundle);
        int response = (bundle == null ? -1 : bundle.getInt("RESPONSE_CODE", -1));
        Log.i(TAG, "Response=" + getResult(response));
        if (response != 0)
            throw new IllegalArgumentException(getResult(response));
        if (!bundle.containsKey("BUY_INTENT"))
            throw new IllegalArgumentException("BUY_INTENT missing");
        return bundle.getParcelable("BUY_INTENT");
    }*/

    public static void setBought(String sku, Context context) {
        Log.i(TAG, "Bought " + sku);
        SharedPreferences prefs = context.getSharedPreferences("IAB", Context.MODE_PRIVATE);
        prefs.edit().putBoolean(sku, true).apply();
    }

    public static boolean isPurchased(String sku, Context context) {
        return true;
        /*try {
            if (Util.isDebuggable(context)) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                return !prefs.getBoolean("debug_iab", false);
            }

            SharedPreferences prefs = context.getSharedPreferences("IAB", Context.MODE_PRIVATE);
            if (ActivityPro.SKU_SUPPORT1.equals(sku) || ActivityPro.SKU_SUPPORT2.equals(sku))
                return prefs.getBoolean(sku, false);

            return (prefs.getBoolean(sku, false) ||
                    prefs.getBoolean(ActivityPro.SKU_PRO1, false) ||
                    prefs.getBoolean(ActivityPro.SKU_DONATION, false));
        } catch (SecurityException ignored) {
            return false;
        }*/
    }

    public static boolean isPurchasedAny(Context context) {
        return true;
        /*try {
            if (Util.isDebuggable(context)) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                return !(prefs.getBoolean("debug_iab", false));
            }

            SharedPreferences prefs = context.getSharedPreferences("IAB", Context.MODE_PRIVATE);
            for (String key : prefs.getAll().keySet())
                if (prefs.getBoolean(key, false))
                    return true;
            return false;
        } catch (SecurityException ignored) {
            return false;
        }*/
    }

    public static String getResult(int responseCode) {
        switch (responseCode) {
            case 0:
                return "OK";
            case 1:
                return "USER_CANCELED";
            case 2:
                return "SERVICE_UNAVAILABLE";
            case 3:
                return "BILLING_UNAVAILABLE";
            case 4:
                return "ITEM_UNAVAILABLE";
            case 5:
                return "DEVELOPER_ERROR";
            case 6:
                return "ERROR";
            case 7:
                return "ITEM_ALREADY_OWNED";
            case 8:
                return "ITEM_NOT_OWNED";
            default:
                return Integer.toString(responseCode);
        }
    }
}
