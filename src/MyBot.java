import java.util.List;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class MyBot {

    public static String generatePrime(int n){
        String  primeNumbers = "";
        for (int i=1; i<n; i++)
        {
            int counter=0;
            for(int num =i; num>=1; num--)
            {
                if(i%num==0)
                {
                    counter = counter + 1;
                }
            }
            if (counter ==2)
            {
                primeNumbers = primeNumbers + i + " ";
            }
        }
        return(primeNumbers);
    }

    public static void main(String args[]) {
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            ConfigurationBuilder b = new ConfigurationBuilder();
            cb.setDebugEnabled(true).setOAuthConsumerKey("IeMkyluSfmRcE1OdEluPHbUnE")
                    .setOAuthConsumerSecret("deLeovZXh4oF9FtdrUVl0LukqdugvAwch8I1joqtI4niZLZfRk")
                    .setOAuthAccessToken("997748161551785985-aRGFcqCmW351MRA8UEnaa7HwGxmzAhi")
                    .setOAuthAccessTokenSecret("6w4Vg4hV6ig59YNSf3MGnBnwNcT7ooOdGwqJLc8uZXtdF");
            b.setDebugEnabled(true).setOAuthConsumerKey("IeMkyluSfmRcE1OdEluPHbUnE")
                    .setOAuthConsumerSecret("deLeovZXh4oF9FtdrUVl0LukqdugvAwch8I1joqtI4niZLZfRk")
                    .setOAuthAccessToken("997748161551785985-aRGFcqCmW351MRA8UEnaa7HwGxmzAhi")
                    .setOAuthAccessTokenSecret("6w4Vg4hV6ig59YNSf3MGnBnwNcT7ooOdGwqJLc8uZXtdF");
            TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
            Twitter twitter = new TwitterFactory(b.build()).getInstance();
            User u = twitter.verifyCredentials();
            StatusListener listener = new StatusListener() {
                @Override
                public void onStatus(Status status) {
                    try {
                        List<Status> statuses = twitter.getMentionsTimeline();
                        System.out.println("Showing @" + u.getScreenName() + "'s mentions.");
                        for (Status status1 : statuses) {
                            System.out.println("status:" + status1);
                            String g=generatePrime(Integer.parseInt(status1.getText().split(" ")[1]));
                            Long id = status1.getId();
                            twitter.updateStatus(new StatusUpdate("@" + status1.getUser().getScreenName() + " " + g).inReplyToStatusId(id));
                            System.out.println("@" + status1.getUser().getScreenName() + " - " + status1.getText());
                        }
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                    System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
                }

                @Override
                public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                    System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
                }

                @Override
                public void onScrubGeo(long userId, long upToStatusId) {
                    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
                }

                @Override
                public void onStallWarning(StallWarning warning) {
                    System.out.println("Got stall warning:" + warning);
                }

                @Override
                public void onException(Exception ex) {
                    System.out.println("Error");
                }

            };
            twitterStream.addListener(listener);
            twitterStream.user();
    }catch(Exception e){
            e.printStackTrace();
        }
    }
}