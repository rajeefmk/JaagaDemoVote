package us.jaaga.demovote.models;

import java.io.Serializable;

public class ProjectListData implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String deliverableId;
	Boolean deliverableStatus;
	String deliverablesDescription;
	String deliverableTitle;
	boolean votingStatus = false;
	
	int totalVotes;
	//String noDeliverableMessage;
	
	int totalUpVote;
	int totalDownVote;
	
	public int getTotalUpVote() {
		return totalUpVote;
	}
	public void setTotalUpVote(int totalUpVote) {
		this.totalUpVote = totalUpVote;
	}
	public int getTotalDownVote() {
		return totalDownVote;
	}
	public void setTotalDownVote(int totalDownVote) {
		this.totalDownVote = totalDownVote;
	}
	public String getDeliverableId() {
		return deliverableId;
	}
	public void setDeliverableId(String deliverableId) {
		this.deliverableId = deliverableId;
	}
	public Boolean getDeliverableStatus() {
		return deliverableStatus;
	}
	public void setDeliverableStatus(Boolean deliverableStatus) {
		this.deliverableStatus = deliverableStatus;
	}
	public String getDeliverablesDescription() {
		return deliverablesDescription;
	}
	public void setDeliverablesDescription(String deliverablesDescription) {
		this.deliverablesDescription = deliverablesDescription;
	}
	public String getDeliverableTitle() {
		return deliverableTitle;
	}
	public void setDeliverableTitle(String deliverableTitle) {
		this.deliverableTitle = deliverableTitle;
	}
	public boolean isVotingStatus() {
		return votingStatus;
	}
	public void setVotingStatus(boolean votingStatus) {
		this.votingStatus = votingStatus;
	}
	public int getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}
	
	
	
	
	/*@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel paramParcel, int paramInt) {
		
		paramParcel.writeString(deliverableId);
		paramParcel.writeString(deliverableTitle);
		paramParcel.writeByte((byte) (deliverableStatus ? 1 : 0)); //if myBoolean == true, byte == 1
		
		//boolean deliverableStatus = (Boolean) source.readValue();
	}
	
	public ProjectListData(Parcel source){
		
		this.deliverableId = source.readString();
		this.deliverableName = source.readString();
		this.deliverableStatus = source.readByte() !=0; //myBoolean == true if byte != 0
	}
	
	public static final Parcelable.Creator<ProjectListData> CREATOR = new Parcelable.Creator<ProjectListData>() {

		@Override
		public ProjectListData createFromParcel(Parcel paramParcel) {
			
			return new ProjectListData(paramParcel);
		}

		@Override
		public ProjectListData[] newArray(int paramInt) {
			
			return new ProjectListData[paramInt];
		}
	};
	
	public ProjectListData(){
		
	}*/
	
	
	
}
