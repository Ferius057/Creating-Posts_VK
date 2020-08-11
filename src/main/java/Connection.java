import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class Connection {
    public String VK_KEY;
    public int USER_ID;
    public VkApiClient vk;
    public UserActor actor;

    public void connection(String VK_KEY, int USER_ID) {
        VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());
        UserActor actor = new UserActor(USER_ID, VK_KEY);
        this.VK_KEY = VK_KEY;
        this.USER_ID = USER_ID;
        this.vk = vk;
        this.actor = actor;
    }
}