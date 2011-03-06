/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
