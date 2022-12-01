package edu.brown.cs.student.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.datasource.ExternalAPIHandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class QuestionairreAPIHandler extends ExternalAPIHandler implements Route {
  QuestionairreResponse t;
  private FormData dataObject;

  public QuestionairreAPIHandler(FormData object){
    super();
    this.dataObject = object;
    // this should get exported
  }

  /**
   * Method that handles get request and outputs a serialized response.
   *
   * @param request - the request to handle
   * @param response - the response to modify
   * @return A serialized success response or error
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {

      // first external http request
//      String QuestionairreResponseJson =
//          this.externalGet("localhost fill in");

//      Moshi moshi = new Moshi.Builder().build();
      // change this to <list<string>> might have to make class
//      QuestionairreResponse t = moshi.adapter(QuestionairreResponse.class).fromJson(QuestionairreResponseJson);
//      this.texts = t.getTexts();


      Moshi moshi = new Moshi.Builder().build();
      QueryParamsMap qm = request.queryMap();
      String data = qm.value("data-vals");
      this.dataObject.loadData(data);
      this.dataObject.dataLoaded(true);
      System.out.println(dataObject.getBooleanLoaded());

      CohereAPIHandler handler = new CohereAPIHandler(dataObject);
      handler.handle(request, response);
      t = moshi.adapter(QuestionairreResponse.class).fromJson(data);

      System.out.println(data);
      System.out.println(t);
      System.out.println(t.getEmail());
      String[] args = {t.getEmail()};

      return data;

      //should this be serialize
    } catch (NullPointerException e) {
      // if pr.properties is null or fr.properties is null
      // i.e. an error from NWS API with points or gridpoints request
      return new ErrBadJsonResponse().serialize();
    } catch (Exception e) {
      // add message later
      e.printStackTrace();
      throw e;
    }
  }

  /** Response object to send, containing success notification and temperature */
  public record QuestionairreSuccessResponse(String result, List<String> texts) {
    public QuestionairreSuccessResponse(List<String> texts) {
      this("success", texts);
    }

    /**
     * @return this response, serialized as Json
     */
    String serialize() {
      try {
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<QuestionairreSuccessResponse> adapter = moshi.adapter(QuestionairreSuccessResponse.class);
        return adapter.toJson(this);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
    }
  }
}
