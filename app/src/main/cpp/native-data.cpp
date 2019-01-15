#include <jni.h>
#include "MediaController.hpp"

using namespace std;
using namespace movies;

movies::MovieController controller;

string extractMoviesToJson(vector<Movie*> movies) {
    string output = "[";
    string separator = "";
    for(int i = 0; i < movies.size(); i++)
    {
        Movie movie = (*movies.at(i));
        output += separator + movie.extractJSON();
        separator = ",";
    }
    output += "]";
    return output;
}

string extractMovieDetailsToJson(MovieController controller) {
    vector<Movie*> movies = controller.getMovies();
    string output = "{";
    string separator = "";
    for (int i = 0; i < movies.size(); i++) {
        Movie movie = (*movies.at(i));
        output += separator;
        output += "\"" + movie.name + "\":" + (*controller.getMovieDetail(movie.name)).extractJSON();
        separator = ",";
    }
    output += "}";
    return output; 
}

extern "C" {
    void Java_com_willy_movies_MovieApplication_initializeData(
            JNIEnv *env,
            jobject
        ) {
        controller = movies::MovieController();
    }

    jstring Java_com_willy_movies_model_NativeRepository_getMovieData(
            JNIEnv *env,
            jobject
            ) {
        string json = extractMoviesToJson(controller.getMovies());
        return (*env).NewStringUTF(json.c_str());
    }

    jstring Java_com_willy_movies_model_NativeRepository_getMovieDetailData(
            JNIEnv *env,
            jobject
            ) {
        string json = extractMovieDetailsToJson(controller);
        return (*env).NewStringUTF(json.c_str());
    }
}
