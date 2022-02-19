package istanbuli.a1c2.behzadi.util.communication;


import istanbuli.a1c2.behzadi.util.IabResult;

public interface BillingSupportCommunication {
    void onBillingSupportResult(int response);
    void remoteExceptionHappened(IabResult result);
}
