package Network;

import Network.Connection.GetDataService;
import Network.Connection.RetrofitClientInstance;

public class ClientInstance {
    public static GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
}
