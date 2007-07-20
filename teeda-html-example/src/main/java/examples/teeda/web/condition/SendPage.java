package examples.teeda.web.condition;

public class SendPage {

	public static final String sendMsg_TRequiredValidator = null;

	protected String replyMsg;

	protected String sendMsg;

	protected boolean needReply;

	public void doSend() {
		if (needReply) {
			replyMsg = "@@hoge@@";
		} else {
			replyMsg = null;
		}
		sendMsg = null;
		needReply = false;
	}

	public boolean isDisplayReplyMsg() {
		return replyMsg != null;
	}

	public String getReplyMsg() {
		return replyMsg;
	}

	public void setReplyMsg(final String replyMsg) {
		this.replyMsg = replyMsg;
	}

	public String getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(final String sendMsg) {
		this.sendMsg = sendMsg;
	}

	public boolean isNeedReply() {
		return needReply;
	}

	public void setNeedReply(final boolean reply) {
		this.needReply = reply;
	}

}
