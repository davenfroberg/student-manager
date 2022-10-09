// This code comes from the Workshop example
// "JsonSerializationDemo" by Paul Carter, 2020
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
