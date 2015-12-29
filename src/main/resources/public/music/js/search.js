var PlayButton = React.createClass({
  getInitialState: function() {
    return {path: undefined};
  },
  handleClick: function(event) {
    this.setState({path: this.props.path});
  },
  render: function() {
    var path = this.state.path;
    if(path === undefined) {
        return (
            <button onClick={this.handleClick}>
                Play
            </button>
        );
    } else {
        var absPath = "../../filesystem/" + path;
        return (
            <audio controls>
                <source src={absPath} type="audio/mpeg"></source>
                Your browser does not support the audio element.
            </audio>
        );
    }
  }
});

var SongRow = React.createClass({
    render: function() {
        return (
            <tr>
                <td>{this.props.song.name}</td>
                <td><PlayButton path={this.props.song.path} key={this.props.song.path}/></td>
            </tr>
        );
    }
});

var MusicTable = React.createClass({
    getInitialState: function() {
        return {
            music: []
        };
    },
    componentDidMount: function() {
        $.get(this.props.source, function(result) {
            var json = JSON.parse(result);
            if(this.isMounted()) {
                this.setState({
                    music: json
                });
            }
        }.bind(this));
    },
    render: function() {
        var rows = [];
        this.state.music.forEach(function(song) {
            if(song.name.indexOf(this.props.filterText) === -1) {
                return;
            }
            rows.push(<SongRow song={song} key={song.name} />);
        }.bind(this));
        return (
           <table>
               <thead>
                   <tr>
                       <th>Name</th>
                   </tr>
               </thead>
               <tbody>{rows}</tbody>
           </table>
        );
    }
});

var SearchBar = React.createClass({
    handleChange: function() {
      this.props.onUserInput(this.refs.filterTextInput.value);
    },
    render: function() {
        return (
            <form>
                <input  type="text"
                        placeholder="Search..."
                        value={this.props.filterText}
                        ref="filterTextInput"
                        onChange={this.handleChange} />
            </form>
        );
    }
});

var FilterableMusicTable = React.createClass({
    getInitialState: function() {
        return {
            filterText: ''
        };
    },
    handleUserInput: function(filterText) {
        this.setState({
            filterText: filterText
        });
    },
    render: function() {
        return (
            <div>
                <SearchBar
                    filterText={this.state.filterText}
                    onUserInput={this.handleUserInput}
                />
                <MusicTable
                    filterText={this.state.filterText}
                    source={this.props.source}
                />
            </div>
        );
    }
});

var API_SOURCE = "http://localhost:4567/fileview/music";

ReactDOM.render(
    <FilterableMusicTable source={API_SOURCE} />,
    document.getElementById('container')
);