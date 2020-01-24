package com.example.pratice;

import java.util.ArrayList;

public interface NetworkResponseListener {


 void notifyNetworkResponseSuccess(ArrayList<employeePOJO> s);
 void notifyNetworkResponseFailure();

}
