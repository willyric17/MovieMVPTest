#include <iostream>
#include <string>
#include <vector>
#include "MediaController.hpp"

using namespace std;
using namespace movies;

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

int main() 
{
    MovieController controller = MovieController();
    cout << extractMoviesToJson(controller.getMovies()) << endl;
    cout << endl;
    MovieDetail* detail = controller.getMovieDetail("Top Gun");
    if (detail != nullptr) {
        cout << detail->extractJSON() << endl;
    }
    return 0;
}